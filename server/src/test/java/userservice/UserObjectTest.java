package userservice;

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
        final int dbid = 1;
        final int level = 1;
        final int jobId = 1;
        final int mapId = 1;
        final int x = 50, y = 100;
        final String userName = "test";
        final int currentExp = 0;
        UserObject user = new UserObject()
                .dbid(dbid)
                .level(level)
                .jobId(jobId)
                .name(userName)
                .mapId(mapId)
                .XY(x, y)
                .currentExp(currentExp);

        assertEquals(dbid, user.getDbid());
        assertEquals(userName, user.getName());
        assertEquals(x, user.getX());
        assertEquals(y, user.getY());
        assertEquals(mapId, user.getMapId());
        assertEquals(level, user.getLevel());
        assertEquals(jobId, user.getJobId());
        assertEquals(currentExp, user.getCurrentExp());
    }


}
