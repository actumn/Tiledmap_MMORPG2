package com.game.server.userservice;

import com.game.server.Server;
import com.game.server.service.Service;
import com.game.server.service.JsonServicePacketFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.LinkedList;

/**
 * Created by Lee on 2016-06-02.
 */
public class MapProxy {
    private Service service;
    private JsonServicePacketFactory packetFactory;

    private long map_id;

    private LinkedList<UserObject> objects = new LinkedList<>();

    public MapProxy(UserService userService, long map_id) {
        this.service = userService;
        this.map_id = map_id;
        this.packetFactory = new JsonServicePacketFactory();
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
        if (Server.serviceMap != null) {
            service.sendPacket(
                    Server.serviceMap.get(Server.MapServiceId),
                    packetFactory.maqRequest(this.map_id, user.getUuid())
            );
        }

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

    public void sendChat(JSONObject packet) {
        for(UserObject u : this.objects) {
            u.getChannel().writeAndFlush(packet.toJSONString() + "\r\n");
        }
    }

    public void mapRes(long uuid, JSONArray npcs) {
        UserObject joinUser = getUser(uuid);
        if (joinUser == null) {
            // some log for it
            return;
        }

        for (Object npcData: npcs) {
            JSONObject npcPacket = (JSONObject) npcData;
            joinUser.getChannel().writeAndFlush(npcPacket.toJSONString() + "\r\n");
        }
    }

    public void attack(UserObject user, JSONObject packet) {
        for(UserObject u : this.objects) {
            if (u == user) continue;
            u.getChannel().writeAndFlush(packet.toJSONString() + "\r\n");
        }
    }

    public UserObject getUser(long entityId) {
        for (UserObject user: objects) {
            if (user.getUuid() == entityId) return user;
        }
        return null;
    }

    public long getMap_id() {
        return map_id;
    }

    public boolean isEmpty() { return this.objects.isEmpty(); }

    public boolean containsUser(UserObject user) {
        return this.objects.contains(user);
    }

    public void damaged(JSONObject packet) {
        packet.remove("map_id");
        for (UserObject user: objects) {
            user.getChannel().writeAndFlush(packet.toJSONString() + "\r\n");
        }
    }

    public void notifyLevelUp(long uuid, int new_level) {
        JSONObject packet = packetFactory.levelUp(uuid, new_level);
        for (UserObject user: objects) {
            user.getChannel().writeAndFlush(packet.toJSONString() + "\r\n");
        }
    }

    public void notifyDead(JSONObject packet) {
        for (UserObject user: objects) {
            user.getChannel().writeAndFlush(packet.toJSONString() + "\r\n");
        }
    }
}
