package com.game.server.mapservice;

import java.util.HashMap;

/**
 * Created by Lee on 2016-06-19.
 */
public class MapService {
    private static MapService instance;
    private HashMap<Integer, Map> maps = new HashMap<>();
    public MapService() { instance = this; }

    static public Map getMap(int mapId) {
        return instance.maps.get(mapId);
    }
}
