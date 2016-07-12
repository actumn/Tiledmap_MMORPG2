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
        "entity_id": entityId
    }
    */
    @Override
    public JSONObject maqRequest(long mapId, long entityId) {
        JSONObject obj = new JSONObject();
        obj.put("type", "mapReq");
        obj.put("map_id", mapId);
        obj.put("entity_id", entityId);

        return obj;
    }

    /*
    {
        "type": "mapRes",
        "map_id": mapId
        "entity_id": entityId
        "npcs": [
            {},
            {}
        ]
    }
     */
    @Override
    public JSONObject mapResponse(long mapId, long entityId, JSONArray npcs) {
        return null;
    }

}
