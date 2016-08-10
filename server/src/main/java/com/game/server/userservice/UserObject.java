package com.game.server.userservice;

import com.game.server.Server;
import io.netty.channel.Channel;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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

    public UserObject() {
        this.inventory = new Inventory();
        this.uuid = Server.uniqueId+=1;
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

    public void levelUp() {

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

}
