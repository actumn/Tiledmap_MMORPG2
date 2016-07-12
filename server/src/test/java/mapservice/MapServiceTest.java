package mapservice;

import com.game.server.mapservice.MapService;
import org.json.simple.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Lee on 2016-06-23.
 */
public class MapServiceTest {
    @Test
    public void constructTest() {
        MapService mapService = new MapService();
        assertNull(mapService.pollPacket());
        mapService.addPacket(new JSONObject());
        assertNotNull(mapService.pollPacket());
    }

}
