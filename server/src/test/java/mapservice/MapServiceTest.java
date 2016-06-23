package mapservice;

import com.game.server.mapservice.MapService;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Lee on 2016-06-23.
 */
public class MapServiceTest {
    @Test
    public void constructTest() {
        MapService mapService = new MapService();
        assertNotNull(mapService);
        assertNull(MapService.getMap(1));
    }

}
