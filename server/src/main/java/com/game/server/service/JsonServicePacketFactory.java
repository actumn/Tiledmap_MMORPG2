package com.game.server.service;

import com.game.server.mapservice.NPCObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import protocol.Packet.JsonPacketFactory;

import java.util.Collection;

/**
 * Created by Lee on 2016-07-08.
 */
public class JsonServicePacketFactory extends JsonPacketFactory implements ServicePacketFactory {

    /*
    {
        "type": "mapReq",
        "map_id": mapId,
        "uuid": uuid
    }
    */
    @Override
    public JSONObject maqRequest(long mapId, long uuid) {
        JSONObject obj = new JSONObject();
        obj.put("type", "mapReq");
        obj.put("map_id", mapId);
        obj.put("uuid", uuid);

        return obj;
    }

    /*
    {
        "type": "mapRes",
        "map_id": mapId
        "uuid": uuid
        "npcs": [
            {},
            {}
        ]
    }
     */
    @Override
    public JSONObject mapResponse(long mapId, long uuid, JSONArray npcs) {
        JSONObject obj = new JSONObject();
        obj.put("type", "mapRes");
        obj.put("map_id", mapId);
        obj.put("uuid", uuid);
        obj.put("npcs", npcs);

        return obj;
    }

}
