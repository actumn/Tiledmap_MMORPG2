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

    private LinkedList<UserObject> objects = new LinkedList<>();

    public MapProxy(int map_id) {
        this.map_id = map_id;
        this.packetFactory = new JsonPacketFactory();
    }


    public void moveObject(UserObject user, JSONObject packet) {
        for(UserObject u : this.objects) {
            if (u == user) continue;
            u.getChannel().writeAndFlush(packet.toJSONString()+"\r\n");
        }

        int dest_map_id = Integer.parseInt(String.valueOf(packet.get("dest_map_id")));
        int dx = Integer.parseInt(String.valueOf(packet.get("dest_x")));
        int dy = Integer.parseInt(String.valueOf(packet.get("dest_y")));
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

            u.getChannel().writeAndFlush(packetFactory.character(user.getUuid(),
                    user.getName(), user.getLevel(), user.getJobId()).toJSONString()+"\r\n");
            u.getChannel().writeAndFlush(packetFactory.move(user.getUuid(), user.getMapId(),
                    user.getX(), user.getY()).toJSONString()+"\r\n");

            user.getChannel().writeAndFlush(packetFactory.character(u.getUuid(),
                    u.getName(), u.getLevel(), u.getJobId()).toJSONString()+"\r\n");
            user.getChannel().writeAndFlush(packetFactory.move(u.getUuid(), u.getMapId(),
                    u.getX(), u.getY()).toJSONString()+"\r\n");
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
