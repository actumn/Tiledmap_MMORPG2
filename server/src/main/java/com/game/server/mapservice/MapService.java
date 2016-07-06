package com.game.server.mapservice;

import com.game.server.service.Service;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Lee on 2016-06-19.
 */
public class MapService implements Service {
    private ConcurrentLinkedQueue<JSONObject> servicePacketQueue = new ConcurrentLinkedQueue<>();

    /* properites */
    // key : mapId, value : mapdata
    HashMap<Integer, Map> maps = new HashMap<>();


    /* implements */
    // Constructor
    public MapService() {
        init();
    }
    private void init () {
        //XmlDataLoader dataLoader = new XmlDataLoader();
        //this.maps = dataLoader.loadMaps(this);
    }

    public Map getMap(int mapId) {
        return maps.get(mapId);
    }

    @Override
    public void start() throws Exception {
        System.out.println("MapService start.");

        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    regenLoop();
                    updateLoop();

                    JSONObject packet = servicePacketQueue.poll();

                }
            }
        }).start();
        */
    }

    private void regenLoop() {
        for(Entry<Integer, Map> mapEntry : maps.entrySet()) {
            mapEntry.getValue().regenNPC();
        }
    }
    private void updateLoop() {

    }
}
