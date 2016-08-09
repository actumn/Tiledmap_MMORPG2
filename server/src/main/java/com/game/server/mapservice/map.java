package com.game.server.mapservice;

import com.game.server.Server;
import com.game.server.service.JsonServicePacketFactory;
import com.game.server.service.ServicePacketFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;

/**
 * Created by Lee on 2016-06-19.
 */
public class Map {
    private ServicePacketFactory servicePacketFactory;
    private MapService service;
    private long mapId;
    private int tileWidth, tileHeight;
    private int mapWidth, mapHeight;
    private HashMap<Long, NPCObject> npcObjects = new HashMap<>();

    public Map() {
        this.servicePacketFactory = new JsonServicePacketFactory();
    }
    public Map mapId(long mapId) {
        this.mapId = mapId;
        return this;
    }
    public Map mapService(MapService mapService) {
        this.service = mapService;
        return this;
    }
    public Map size(int tileWidth, int tileHeight, int width, int height) {
        this.tileWidth = tileWidth; this.tileHeight = tileHeight;
        this.mapWidth = width; this.mapHeight = height;
        return this;
    }
    public void addNPC(NPCObject npcObject) {
        this.npcObjects.put(npcObject.getEntityId(), npcObject);
    }


    public void mapRes(long entityId) {
        JSONArray npcJsonArray = new JSONArray();

        for(NPCObject npc: npcObjects.values()) {
            if (npc.isDead()) continue;

            JSONObject npcData = servicePacketFactory.npc(npc.getEntityId(), npc.getNpcId(),
                    npc.getHp(), npc.getMp(), npc.getX(), npc.getY());

            npcJsonArray.add(npcData);
        }

        service.sendPacket(
                Server.serviceMap.get(Server.UserServiceId),
                servicePacketFactory.mapResponse(mapId, entityId, npcJsonArray)
        );
    }


    public void damaging(JSONObject packet) {
        long entityId = (long) packet.get("id");
        long targetId = (long) packet.get("target_id");
        int damage = (int)(long) packet.get("damage");

        NPCObject target = npcObjects.get(targetId);
        target.damaged(damage);

        this.service.sendPacket(
                Server.serviceMap.get(Server.UserServiceId),
                servicePacketFactory.damaged(this.mapId, targetId, target.getHp())
        );

        if (target.isDead()) {
            // add exp module here.
        }
    }



    public NPCObject getNpcById(long entityId) {
        return npcObjects.get(entityId);
    }

    public void regenNPC() {
        npcObjects.values().forEach(NPCObject::regen);
    }

    public int atTileX(int tileX) {
        return this.tileWidth * tileX;
    }
    public int atTileY(int tileY) {
        return this.tileHeight * tileY;
    }


    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getTotalWidth() {
        return mapWidth * tileWidth;
    }
    public int getTotalHeight() {
        return mapHeight * tileHeight;
    }

    public long getMapId() {
        return mapId;
    }

    public MapService getService() {
        return service;
    }

    public boolean containsNPC(NPCObject npc) {
        return this.npcObjects.containsValue(npc);
    }

    public HashMap<Long, NPCObject> TEST_getNpcObjects() {
        return npcObjects;
    }
}
