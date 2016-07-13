package service;

import com.game.server.service.JsonServicePacketFactory;
import com.game.server.service.ServicePacketFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Lee on 2016-07-10.
 */
public class JsonServicePacketFactoryTest {
    @Test
    public void mapRequestTest() {
        ServicePacketFactory servicePacketFactory = new JsonServicePacketFactory();

        long expect_map_id = 1;
        long expect_uuid = 2;
        JSONObject mapReqPacket = servicePacketFactory.maqRequest(expect_map_id, expect_uuid);
        assertNotNull(mapReqPacket);

        long actual_map_id = (long) mapReqPacket.get("map_id");
        long actual_uuid = (long) mapReqPacket.get("uuid");

        assertEquals(expect_map_id, actual_map_id);
        assertEquals(expect_uuid, actual_uuid);
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
    @Test
    public void mapResponseTest() {
        ServicePacketFactory servicePacketFactory = new JsonServicePacketFactory();

        long expect_npc_entity_id = 40;
        long expect_npc_id = 30;
        int expect_npc_hp = 50;
        int expect_npc_mp = 60;
        int expect_npc_x = 70;
        int expect_npc_y = 80;
        JSONObject expect_npc1 = servicePacketFactory.npc(expect_npc_entity_id, expect_npc_id,
                expect_npc_hp, expect_npc_mp, expect_npc_x, expect_npc_y);

        long expect_map_id = 1;
        long expect_uuid = 2;
        JSONArray expect_npcs = new JSONArray();
        expect_npcs.add(expect_npc1);

        JSONObject packet= servicePacketFactory.mapResponse(expect_map_id, expect_uuid, expect_npcs);

        long actual_map_id = (long) packet.get("map_id");
        long actual_uuid = (long) packet.get("uuid");
        JSONArray actual_npcs = (JSONArray) packet.get("npcs");
        JSONObject actual_npc1 = (JSONObject) actual_npcs.get(0);

        assertEquals(expect_map_id, actual_map_id);
        assertEquals(expect_uuid, actual_uuid);
        assertEquals(expect_npcs, actual_npcs);
        assertEquals(expect_npc1, actual_npc1);

    }
}
