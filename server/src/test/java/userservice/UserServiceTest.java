package userservice;

import com.game.server.userservice.MapProxy;
import com.game.server.userservice.UserObject;
import com.game.server.userservice.UserService;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Lee on 2016-06-02.
 */
public class UserServiceTest {

    @Test
    public void getMapProxyTest() {
        final int mapId = 1;

        UserService userService = new UserService(6112);

        MapProxy newMap = userService.getMapProxy(mapId, true);
        assertNotNull(newMap);
        assertEquals(mapId, newMap.getMap_id());
    }

    @Test
    public void loginUserTest() {
        final int mapId = 1;
        final int x = 50, y = 100;
        EmbeddedChannel testChannel = new EmbeddedChannel();
        UserObject user = new UserObject()
                .channel(testChannel)
                .name("test")
                .mapId(mapId)
                .XY(x, y);
        assertNotNull(user);

        UserService userService = new UserService(6112);

        MapProxy newMap = userService.getMapProxy(mapId, true);
        assertNotNull(newMap);
        assertEquals(mapId, newMap.getMap_id());

        userService.loginUser(user);
        assertEquals(user.getMapId(), newMap.getMap_id());


        UserObject user2 = new UserObject()
                .channel(testChannel)
                .name("test2")
                .mapId(mapId)
                .XY(x, y);

        testChannel.finish();
    }
}
