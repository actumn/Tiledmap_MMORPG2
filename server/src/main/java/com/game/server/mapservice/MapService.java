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
    HashMap<Long, Map> maps = new HashMap<>();


    /* implements */
    // Constructor
    public MapService() {}
    public MapService init () {
        MapDataLoader dataLoader = new MapDataLoader();
        this.maps = dataLoader.loadMaps(this);
        return this;
    }

    public Map getMap(long mapId) {
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
        maps.values().forEach(Map::regenNPC);
    }
    private void updateLoop() {

    }


    @Override
    public void addPacket(JSONObject packet) {
        this.servicePacketQueue.add(packet);
    }
    @Override
    public JSONObject pollPacket() {
        return this.servicePacketQueue.poll();
    }

    @Override
    public void sendPacket(Service service, JSONObject object) {
        service.addPacket(object);
    }
}
