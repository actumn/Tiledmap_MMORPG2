package com.game.server.mapservice;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Lee on 2016-06-19.
 */
public class Map {
    private MapService mapService;
    private int mapId;
    private int mapWidth, mapHeight;
    private HashMap<Integer, MobObject> mobObjects = new HashMap<>();

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
}
