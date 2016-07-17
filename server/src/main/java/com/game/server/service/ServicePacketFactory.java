package com.game.server.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import protocol.Packet.PacketFactory;

import java.util.Collection;

/**
 * Created by Lee on 2016-07-08.
 */
public interface ServicePacketFactory extends PacketFactory {
    JSONObject maqRequest(long mapId, long uuid);
    JSONObject mapResponse(long mapId, long uuid, JSONArray npcs);
    JSONObject damaged(long mapId, long entityId, int hp);
    JSONObject addExp(long uuid, int exp);
}
