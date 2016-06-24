package com.game.server;

import com.game.server.mapservice.MapService;
import com.game.server.service.Service;
import com.game.server.userservice.UserService;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Map;

/**
 * Created by Lee on 2016-06-01.
 */
public class Server {
    public static int unique;
    public static HashMap<Integer, Service> serviceMap;
    public static void main(String[] args) throws Exception {
        serviceMap = new HashMap<>();

        serviceMap.put(UserService.serviceId, new UserService(6112));
        serviceMap.put(MapService.serviceId, new MapService());

        for(Entry<Integer, Service> entry: serviceMap.entrySet()) {
            entry.getValue().start();
        }

    }
}
