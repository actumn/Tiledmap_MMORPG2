package com.game.server.mapservice;

import com.game.server.service.Service;

import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Created by Lee on 2016-06-19.
 */
public class MapService implements Service {

    /* properites */
    // key : mapId, value : mapdata
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
    public void start() throws Exception {
        System.out.println("MapService start.");

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    regenLoop();
                    updateLoop();
                }
            }
        }).start();
    }

    private long regenTime;
    private void regenLoop() {
        if (System.currentTimeMillis() < regenTime) return;

        for(Entry<Integer, Map> mapEntry : maps.entrySet()) {
            mapEntry.getValue().regenNPC();
        }
        regenTime += System.currentTimeMillis() + 10 * 1000;
    }
    private void updateLoop() {

    }
}
