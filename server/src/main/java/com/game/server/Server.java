package com.game.server;

import com.game.server.mapservice.MapService;
import com.game.server.service.Service;
import com.game.server.userservice.UserService;

import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Created by Lee on 2016-06-01.
 */
public class Server {
    public static final int MapServiceId = 1;
    public static final int UserServiceId = 2;

    public static long uniqueId = 0;
    public static HashMap<Integer, Service> serviceMap;

    public static void main(String[] args) throws Exception {
        serviceMap = new HashMap<>();

        serviceMap.put(MapServiceId, new MapService().init());
        serviceMap.put(UserServiceId, new UserService(6112));

        for(Service service: serviceMap.values()) {
            service.start();
        }

    }
}
