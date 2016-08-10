package com.game.server.userservice;

import com.game.server.Server;
import com.game.server.db.DBManager;
import io.netty.channel.Channel;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import protocol.Packet.JsonPacketFactory;
import protocol.Packet.PacketFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Lee on 2016-06-01.
 */
public class UserObject {
    private Channel channel;
    private int dbid;
    private long uuid;
    private MapProxy map;
    private long mapId;
    private int level;
    private int currentExp;
    private int maxExp;
    private int jobId;
    private String name;
    private int x,y;
    private Inventory inventory;

    private PacketFactory packetFactory;

    public UserObject(PacketFactory packetFactory) {
        this.inventory = new Inventory();
        this.uuid = Server.uniqueId+=1;
        this.packetFactory = packetFactory;
    }
    public UserObject dbid(int dbid) {
        this.dbid = dbid;
        return this;
    }
    public UserObject channel(Channel channel) {
        this.channel = channel;
        return this;
    }
    public UserObject name(String name) {
        this.name = name;
        return this;
    }
    public UserObject level(int level) {
        this.level = level;
        return this;
    }
    public UserObject currentExp(int currentExp) {
        this.currentExp = currentExp;
        return this;
    }
    public UserObject maxExp(int maxExp) {
        this.maxExp = maxExp;
        return this;
    }
    public UserObject jobId(int jobId) {
        this.jobId = jobId;
        return this;
    }

    public UserObject mapId(int mapId) {
        this.mapId = mapId;
        return this;
    }
    public UserObject XY(int x, int y) {
        this.x = x; this.y = y;
        return this;
    }


    public void initMap(MapProxy map) {
        this.map = map;
        this.map.joinUser(this);

    }

    public MapProxy getMap() {
        return map;
    }


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    public long getUuid() {
        return uuid;
    }

    public int getDbid() {
        return dbid;
    }

    public Channel getChannel() {
        return channel;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public long getMapId() {
        return mapId;
    }

    public String getName() {
        return name;
    }

    public int getJobId() {
        return jobId;
    }

    public void setMap(MapProxy map) {
        this.map = map;
        this.mapId = map.getMap_id();
    }

    public int getLevel() {
        return level;
    }

    public int getCurrentExp() {
        return currentExp;
    }

    public void addExp(JSONObject packet) {
        int exp = (int) packet.get("exp");
        this.currentExp += exp;
        this.channel.writeAndFlush(packetFactory.updateExp(currentExp).toJSONString()+"\r\n");
        if (currentExp >= maxExp) levelUp();
    }
    private void levelUp() {
        if (currentExp < maxExp) return;

        this.currentExp -= maxExp;
        this.channel.writeAndFlush(packetFactory.updateExp(currentExp).toJSONString()+"\r\n");
        this.level += 1;
        this.map.notifyLevelUp(uuid, level);
        this.maxExp = nextMaxExp(this.level);
    }

    private int nextMaxExp(int level) {
        int maxExp = 4*level;
        try {
            Connection con = DBManager.getConnection();
            String sql = "SELECT EXP FROM EXPS WHERE LEVEL=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, level);

            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                maxExp = rs.getInt("exp");
            } else {
                maxExp = 99999999;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            maxExp = 99999999;
        }
        finally {
            return maxExp;
        }
    }
}
