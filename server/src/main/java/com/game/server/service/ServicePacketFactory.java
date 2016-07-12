package com.game.server.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Collection;

/**
 * Created by Lee on 2016-07-08.
 */
public interface ServicePacketFactory {
    JSONObject maqRequest(long mapId, long entityId);
    JSONObject mapResponse(long mapId, long entityId, JSONArray npcs);
}
