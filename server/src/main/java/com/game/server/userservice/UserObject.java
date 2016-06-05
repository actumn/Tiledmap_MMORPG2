package com.game.server.userservice;

import io.netty.channel.Channel;
import protocol.Packet.JsonPacketFactory;

/**
 * Created by Lee on 2016-06-01.
 */
public class UserObject {
    private Channel channel;
    private long uuid;
    private MapProxy map;
    private int loginMapId;
    private int level;
    private int jobId;
    private String name;
    private int x,y;
    private Inventory inventory;

    public UserObject() {
        this.inventory = new Inventory();
    }
    public UserObject uuid(long uuid) {
        this.uuid = uuid;
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
    public UserObject jobId(int jobId) {
        this.jobId = jobId;
        return this;
    }

    public UserObject mapId(int mapId) {
        this.loginMapId = mapId;
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



    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    public long getUuid() {
        return uuid;
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

    public int getMapId() {
        return loginMapId;
    }

    public String getName() {
        return name;
    }

    public int getJobId() {
        return jobId;
    }

    public void setMap(MapProxy map) {
        this.map = map;
    }

    public int getLevel() {
        return level;
    }

}
