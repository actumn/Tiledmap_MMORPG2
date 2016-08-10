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
        ServicePacketFactory testPacketFactory = new JsonServicePacketFactory();

        String expect_type = "mapReq";
        long expect_map_id = 1;
        long expect_uuid = 2;
        JSONObject mapReqPacket = testPacketFactory.maqRequest(expect_map_id, expect_uuid);
        assertNotNull(mapReqPacket);

        String actual_type = (String) mapReqPacket.get("type");
        long actual_map_id = (long) mapReqPacket.get("map_id");
        long actual_uuid = (long) mapReqPacket.get("uuid");

        assertEquals(expect_type, actual_type);
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
        ServicePacketFactory testPacketFactory = new JsonServicePacketFactory();

        String expect_type = "mapRes";
        long expect_npc_entity_id = 40;
        long expect_npc_id = 30;
        int expect_npc_hp = 50;
        int expect_npc_mp = 60;
        int expect_npc_x = 70;
        int expect_npc_y = 80;
        JSONObject expect_npc1 = testPacketFactory.npc(expect_npc_entity_id, expect_npc_id,
                expect_npc_hp, expect_npc_mp, expect_npc_x, expect_npc_y);

        long expect_map_id = 1;
        long expect_uuid = 2;
        JSONArray expect_npcs = new JSONArray();
        expect_npcs.add(expect_npc1);

        JSONObject packet= testPacketFactory.mapResponse(expect_map_id, expect_uuid, expect_npcs);
        assertNotNull(packet);

        String actual_type = (String) packet.get("type");
        long actual_map_id = (long) packet.get("map_id");
        long actual_uuid = (long) packet.get("uuid");
        JSONArray actual_npcs = (JSONArray) packet.get("npcs");
        JSONObject actual_npc1 = (JSONObject) actual_npcs.get(0);

        assertEquals(expect_type, actual_type);
        assertEquals(expect_map_id, actual_map_id);
        assertEquals(expect_uuid, actual_uuid);
        assertEquals(expect_npcs, actual_npcs);
        assertEquals(expect_npc1, actual_npc1);
    }


    @Test
    public void damagedTest() {
        ServicePacketFactory testPacketFactory = new JsonServicePacketFactory();

        String expect_type = "damaged";
        long expect_map_id = 1;
        long expect_entity_id = 2;
        int expect_hp = 60;

        JSONObject packet = testPacketFactory.damaged(expect_map_id, expect_entity_id, expect_hp);
        assertNotNull(packet);

        String actual_type = (String) packet.get("type");
        long actual_map_id = (long) packet.get("map_id");
        long actual_entity_id = (long) packet.get("entity_id");
        int actual_hp = (int) packet.get("hp");

        assertEquals(expect_type, actual_type);
        assertEquals(expect_map_id, actual_map_id);
        assertEquals(expect_entity_id, actual_entity_id);
        assertEquals(expect_hp, actual_hp);
    }

    @Test
    public void addExpTest() {
        ServicePacketFactory testPacketFactory = new JsonServicePacketFactory();

        String expect_type = "addExp";
        long expect_uuid = 10;
        int expect_exp = 100;

        JSONObject packet = testPacketFactory.addExp(expect_uuid, expect_exp);
        assertNotNull(packet);

        String actual_type = (String) packet.get("type");
        long actual_uuid = (long) packet.get("uuid");
        int actual_exp = (int) packet.get("exp");

        assertEquals(expect_type, actual_type);
        assertEquals(expect_uuid, actual_uuid);
        assertEquals(expect_exp, actual_exp);
    }


    @Test
    public void deadTest() {
        ServicePacketFactory testPacketFactory = new JsonServicePacketFactory();

        String expect_type = "dead";
        long expect_map_id = 2;
        long expect_target_id = 40;

        JSONObject packet = testPacketFactory.dead(expect_map_id, expect_target_id);
        assertNotNull(packet);

        String actual_type = (String) packet.get("type");
        long actual_map_id = (long) packet.get("map_id");
        long actual_target_id = (long) packet.get("entity_id");

        assertEquals(expect_type, actual_type);
        assertEquals(expect_map_id, actual_map_id);
        assertEquals(expect_target_id, actual_target_id);
    }
}
