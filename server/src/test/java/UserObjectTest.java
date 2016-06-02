import com.game.server.userservice.UserObject;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by Lee on 2016-06-02.
 */
public class UserObjectTest {

    @Test
    public void initTest() {
        final int mapId = 1;
        final int x = 50, y = 100;
        final String userName = "test";
        EmbeddedChannel someChannel = new EmbeddedChannel();
        UserObject user = new UserObject()
                .setChannel(someChannel)
                .setName(userName)
                .setMapId(mapId)
                .setXY(x, y);

        assertEquals(someChannel, user.getChannel());
        assertEquals(userName, user.getName());
        assertEquals(x, user.getX());
        assertEquals(y, user.getY());
        assertEquals(mapId, user.getMapId());
    }


}
