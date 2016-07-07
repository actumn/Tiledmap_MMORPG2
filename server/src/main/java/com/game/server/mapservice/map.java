package com.game.server.mapservice;

import java.util.HashMap;

/**
 * Created by Lee on 2016-06-19.
 */
public class Map {
    private MapService mapService;
    private long mapId;
    private int tileWidth, tileHeight;
    private int mapWidth, mapHeight;
    private HashMap<Long, NPCObject> npcObjects = new HashMap<>();

    public Map() {}
    public Map mapId(long mapId) {
        this.mapId = mapId;
        return this;
    }
    public Map mapService(MapService mapService) {
        this.mapService = mapService;
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


    public NPCObject getNpcById(long entityId) {
        return npcObjects.get(entityId);
    }

    public void regenNPC() {

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

    public MapService getMapService() {
        return mapService;
    }

    public boolean containsNPC(NPCObject npc) {
        return this.npcObjects.containsValue(npc);
    }

    public HashMap<Long, NPCObject> TEST_getNpcObjects() {
        return npcObjects;
    }
}
