package com.game.server.mapservice;

import java.util.HashMap;

/**
 * Created by Lee on 2016-06-19.
 */
public class Map {
    private MapService mapService;
    private int mapId;
    private int mapWidth, mapHeight;
    private HashMap<Long, NPCObject> npcObjects = new HashMap<>();

    public Map() {}
    public Map mapId(int mapId) {
        this.mapId = mapId;
        return this;
    }
    public Map mapService(MapService mapService) {
        this.mapService = mapService;
        return this;
    }
    public Map size(int mapWidth, int mapHeight) {
        this.mapWidth = mapWidth; this.mapHeight = mapHeight;
        return this;
    }
    public void addNPC(NPCObject npcObject) {
        this.npcObjects.put(npcObject.getEntityId(), npcObject);
    }


    public NPCObject getNpcById(long entityId) {
        return npcObjects.get(entityId);
    }

    public void regenNPC() {

    }


    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapId() {
        return mapId;
    }

    public MapService getMapService() {
        return mapService;
    }
}
