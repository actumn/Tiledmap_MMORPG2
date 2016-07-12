package service;

import com.game.server.service.JsonServicePacketFactory;
import com.game.server.service.ServicePacketFactory;
import org.json.simple.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Lee on 2016-07-10.
 */
public class JsonServicePacketFactoryTest {
    @Test
    public void mapReqTest() {
        ServicePacketFactory servicePacketFactory = new JsonServicePacketFactory();

        long expect_map_id = 1;
        long expect_entity_id = 2;
        JSONObject mapReqPacket = servicePacketFactory.maqRequest(expect_map_id, expect_entity_id);
        assertNotNull(mapReqPacket);

        long actual_map_id = (long) mapReqPacket.get("map_id");
        long actual_entity_id = (long) mapReqPacket.get("entity_id");

        assertEquals(expect_map_id, actual_map_id);
        assertEquals(expect_entity_id, actual_entity_id);
    }
}
