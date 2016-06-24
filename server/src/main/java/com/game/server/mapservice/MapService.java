package com.game.server.mapservice;

import com.game.server.XmlDataLoader;
import com.game.server.service.Service;

import java.util.HashMap;

/**
 * Created by Lee on 2016-06-19.
 */
public class MapService implements Service {
    public static final int serviceId = 2;

    /* properites */
    // key : mapId, value : map
    HashMap<Integer, Map> maps = new HashMap<>();


    /* implements */
    // Constructor
    public MapService() {
        init();
    }
    private void init () {
        XmlDataLoader dataLoader = new XmlDataLoader();
        this.maps = dataLoader.loadMaps(this);
    }

    public Map getMap(int mapId) {
        return maps.get(mapId);
    }


    @Override
    public void start() {

    }
}
