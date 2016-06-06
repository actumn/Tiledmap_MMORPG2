package com.game.server.userservice;

import org.json.simple.JSONObject;
import protocol.Packet.JsonPacketFactory;

import java.util.LinkedList;

/**
 * Created by Lee on 2016-06-02.
 */
public class MapProxy {
    private JsonPacketFactory packetFactory;

    private int map_id;

    private LinkedList<UserObject> objects = new LinkedList<UserObject>();

    public MapProxy(int map_id) {
        this.map_id = map_id;
        this.packetFactory = new JsonPacketFactory();
    }

    /*
        @Param user : UserObject. character of user
        @Param packet template :
        { "type" : "move"
            "mapId" : 1
            "sx" : 100
            "sy" : 100
            "dx" : 110
            "dy" : 110 } (json)
     */
    public void moveObject(UserObject user, JSONObject packet) {
        for(UserObject u : this.objects) {
            if (u == user) continue;
            u.getChannel().write(packet);
        }

        int dest_map_id = Integer.parseInt(String.valueOf(packet.get("dest_map_id")));
        int dx = Integer.parseInt(String.valueOf(packet.get("dx")));
        int dy = Integer.parseInt(String.valueOf(packet.get("dy")));
        user.setX(dx);
        user.setY(dy);
        if (user.getMapId() == dest_map_id) return;
        if (dest_map_id == getMap_id()) {
            this.joinUser(user);
        }
        else {
            this.exitUser(user);
        }
    }

    public void joinUser(UserObject user) {
        this.objects.add(user);
        user.setMap(this);

        for (UserObject u : this.objects) {
            if (u == user) continue;;

            u.getChannel().write(packetFactory.character(user.getUuid(),
                    user.getName(), user.getLevel(), user.getLevel()));
            u.getChannel().write(packetFactory.move(user.getUuid(), user.getMapId(),
                    user.getX(), user.getY()));
        }
    }
    public void exitUser(UserObject user) {
        this.objects.remove(user);
    }

    public void sendChat() {

    }

    public int getMap_id() {
        return map_id;
    }

    public boolean isEmpty() { return this.objects.isEmpty(); }

    public boolean containsUser(UserObject user) {
        return this.objects.contains(user);
    }
}
