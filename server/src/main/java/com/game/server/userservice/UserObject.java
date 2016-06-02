package com.game.server.userservice;

import io.netty.channel.Channel;

/**
 * Created by Lee on 2016-06-01.
 */
public class UserObject {
    public static int uniqueId;

    private Channel channel;
    private long uuid;
    private MapProxy map;
    private int loginMapId;
    private String name;
    private int x,y;
    private Inventory inventory;

    public UserObject() { this.inventory = new Inventory(); }
    public UserObject setChannel(Channel channel) {
        this.channel = channel;
        return this;
    }
    public UserObject setName(String name) {
        this.name = name;
        return this;
    }
    public UserObject setMapId(int mapId) {
        this.loginMapId = mapId;
        return this;
    }
    public UserObject setXY(int x, int y) {
        this.x = x; this.y = y;
        return this;
    }


    public void moveMap(MapProxy map, int x, int y) {
        this.map = map;
        this.x = x;
        this.y = y;

        map.joinUser(this);


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

    public void setMap(MapProxy map) {
        this.map = map;
    }
}
