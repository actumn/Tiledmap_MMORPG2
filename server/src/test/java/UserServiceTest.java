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
    public void mapProxyTest() {
        final int mapId = 1;

        UserService userService = new UserService();

        MapProxy newMap = userService.getMapProxy(mapId, true);
        assertNotNull(newMap);
        assertEquals(mapId, newMap.getMap_id());
    }

    @Test
    public void loginUserTest() {
        final int mapId = 1;
        final int x = 50, y = 100;
        EmbeddedChannel someChannel = new EmbeddedChannel();
        UserObject user = new UserObject()
                .setChannel(someChannel)
                .setName("test")
                .setMapId(mapId)
                .setXY(x, y);
        assertNotNull(user);

        UserService userService = new UserService();

        MapProxy newMap = userService.getMapProxy(mapId, true);
        assertNotNull(newMap);
        assertEquals(mapId, newMap.getMap_id());

        userService.loginUser(user);
        assertEquals(user.getMapId(), newMap.getMap_id());
    }
}
