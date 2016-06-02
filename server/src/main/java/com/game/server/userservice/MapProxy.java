package com.game.server.userservice;

import org.json.simple.JSONObject;

import java.util.LinkedList;

/**
 * Created by Lee on 2016-06-02.
 */
public class MapProxy {

    private int map_id;

    private LinkedList<UserObject> objects = new LinkedList<UserObject>();

    public MapProxy(int map_id) {
        this.map_id = map_id;
    }

    /*
        this method receive packet as JSON like
        {
            "type" : "move"
            "mapId" : 1
            "sx" : 100
            "sy" : 100
            "dx" : 110
            "dy" : 110
        }
     */
    public void moveObject(UserObject user, JSONObject packet) {
        for(UserObject u : this.objects) {
            if (u == user) continue;
            u.getChannel().write(packet);
        }

        int dx = Integer.parseInt(String.valueOf(packet.get("dx")));
        int dy = Integer.parseInt(String.valueOf(packet.get("dy")));
        user.setX(dx);
        user.setY(dy);
    }

    public void joinUser(UserObject user) {
        this.objects.add(user);
        user.setMap(this);

        for (UserObject u : this.objects) {
            if (u == user) continue;;

            //u.getChannel().write()
        }
    }
    public void exitUser(UserObject user) {

    }

    public void sendChat() {

    }

    public int getMap_id() {
        return map_id;
    }
}
