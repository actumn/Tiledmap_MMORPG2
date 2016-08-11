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

    /*
        {
            "type": "regen"
            "map_id": mapId
            "uuid": uuid
            "npcs": [
                {}
            ]
        }
     */
    @Override
    public JSONObject regen(long mapId, JSONArray npcs) {
        JSONObject obj = new JSONObject();
        obj.put("type", "regen");
        obj.put("map_id", mapId);
        obj.put("npcs", npcs);

        return obj;
    }

    /*
            {
                "type": "damaged"
                "map_id": mapId
                "entity_id": entityId
                "hp": hp
                "damage": damage
            }
         */
    @Override
    public JSONObject damaged(long mapId, long entityId, int hp) {
        JSONObject obj = new JSONObject();
        obj.put("type", "damaged");
        obj.put("map_id", mapId);
        obj.put("entity_id", entityId);
        obj.put("hp", hp);

        return obj;
    }

    /*
        {
            "type":"addExp"
            "uuid":uuid
            "exp":exp
        }
     */
    @Override
    public JSONObject addExp(long uuid, int exp) {
        JSONObject obj = new JSONObject();
        obj.put("type", "addExp");
        obj.put("uuid", uuid);
        obj.put("exp", exp);

        return obj;
    }

    /*
        {
            "type": "regen"
        }
     */


    /*
        {
            "type": "dead"
            "map_id": mapId
            "entity_id": entityId
        }
     */
    @Override
    public JSONObject dead(long mapId, long entityId) {
        JSONObject obj = new JSONObject();
        obj.put("type", "dead");
        obj.put("map_id", mapId);
        obj.put("entity_id", entityId);
        return obj;
    }

}
