package src;

import org.json.simple.JSONObject;
import org.junit.Test;
import protocol.Packet.JsonPacketFactory;

import static org.junit.Assert.*;

/**
 * Created by Lee on 2016-06-04.
 */
public class JsonPacketFactoryTest {

    @Test
    public void joinTest() {
        JsonPacketFactory testFactory = new JsonPacketFactory();
        assertNotNull(testFactory);
        final String expect_type = "join";
        final String expect_user_id = "admin";
        final String expect_user_pw = "1234";
        final String expect_user_name = "홍길동";
        final int expect_job_id = 1;

        JSONObject obj = testFactory.join(expect_user_id, expect_user_pw, expect_user_name, expect_job_id);
        assertNotNull(obj);

        String actual_type = (String) obj.get("type");
        String actual_user_id = (String) obj.get("user_id");
        String actual_user_pw = (String) obj.get("user_pw");
        String actual_user_name = (String) obj.get("user_name");
        int actual_job_id = (int) obj.get("job_id");

        assertEquals(expect_type, actual_type);
        assertEquals(expect_user_id, actual_user_id);
        assertEquals(expect_user_pw, actual_user_pw);
        assertEquals(expect_user_name, actual_user_name);
        assertEquals(expect_job_id, actual_job_id);
    }

    @Test
    public void loginTest() {
        JsonPacketFactory testFactory = new JsonPacketFactory();
        assertNotNull(testFactory);
        final String expect_type = "login";
        final String expect_user_id = "admin";
        final String expect_user_pw = "1234";

        JSONObject obj = testFactory.login(expect_user_id, expect_user_pw);
        assertNotNull(obj);

        String actual_type = (String) obj.get("type");
        String actual_user_id = (String) obj.get("user_id");
        String actual_user_pw = (String) obj.get("user_pw");
        assertEquals(expect_type, actual_type);
        assertEquals(actual_user_id, actual_user_id);
        assertEquals(actual_user_pw, actual_user_pw);
    }

    @Test
    public void characterTest() {
        JsonPacketFactory testFactory = new JsonPacketFactory();
        assertNotNull(testFactory);

        final String expect_type = "character";
        final long expect_id = 1343;
        final String expect_name = "홍길동";
        final int expect_level = 1;
        final int expect_job_id = 1;

        JSONObject obj = testFactory.character(expect_id, expect_name, expect_level, expect_job_id);
        assertNotNull(obj);

        String actual_type = (String) obj.get("type");
        long actual_id = (long) obj.get("id");
        String actual_name = (String) obj.get("name");
        int actual_level = (int) obj.get("level");
        int actual_job_id = (int) obj.get("job_id");

        assertEquals(expect_type, actual_type);
        assertEquals(expect_id, actual_id);
        assertEquals(expect_name, actual_name);
        assertEquals(expect_level, actual_level);
        assertEquals(expect_job_id, actual_job_id);
    }

    @Test
    public void moveTest() {
        JsonPacketFactory testFactory = new JsonPacketFactory();
        assertNotNull(testFactory);

        final String expect_type = "move";
        final long expect_id = 1343;
        final int expect_map_id = 1;
        final int expect_dest_x = 105;
        final int expect_dest_y = 100;

        JSONObject obj = testFactory.move(expect_id, expect_map_id, expect_dest_x,expect_dest_y);
        assertNotNull(obj);

        String actual_type = (String) obj.get("type");
        long actual_id = (long) obj.get("id");
        int actual_map_id = (int) obj.get("dest_map_id");
        int actual_dest_x = (int) obj.get("dest_x");
        int actual_dest_y = (int) obj.get("dest_y");

        assertEquals(expect_type, actual_type);
        assertEquals(expect_id, actual_id);
        assertEquals(expect_map_id, actual_map_id);
        assertEquals(expect_dest_x, actual_dest_x);
        assertEquals(expect_dest_y, actual_dest_y);
    }


    @Test
    public void chatTest() {
        JsonPacketFactory testFactory = new JsonPacketFactory();
        assertNotNull(testFactory);

        final String expect_type = "chat";
        final long expect_entity_id = 1;
        final String expect_content = "hello";

        JSONObject obj = testFactory.chat(expect_entity_id, expect_content);
        assertNotNull(obj);

        String actual_type = (String) obj.get("type");
        long actual_entity_id = (long) obj.get("id");
        String actual_content = (String) obj.get("content");

        assertEquals(expect_type, actual_type);
        assertEquals(expect_entity_id, actual_entity_id);
        assertEquals(expect_content, actual_content);
    }


    @Test
    public void notifyTest() {
        JsonPacketFactory testFactory = new JsonPacketFactory();
        assertNotNull(testFactory);

        final String expect_type = "notify";
        final String expect_content = "some notify";

        JSONObject obj = testFactory.notify(expect_content);
        assertNotNull(obj);

        String actual_type = (String) obj.get("type");
        String actual_content = (String) obj.get("content");

        assertEquals(expect_type, actual_type);
        assertEquals(expect_content, actual_content);
    }
}
