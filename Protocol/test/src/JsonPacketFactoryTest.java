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
    public void npcTest() {
        JsonPacketFactory testFactory = new JsonPacketFactory();
        assertNotNull(testFactory);

        final String expect_type = "npc";
        final long expect_id = 1343;
        final long expect_npc_id = 1;
        final int expect_hp = 100;
        final int expect_mp = 0;
        final int expect_x = 10;
        final int expect_y = 40;

        JSONObject obj = testFactory.npc(expect_id, expect_npc_id, expect_hp, expect_mp, expect_x, expect_y);
        String actual_type = (String) obj.get("type");

        long actual_id = (long) obj.get("id");
        long actual_npc_id = (long) obj.get("npc_id");
        int actual_hp = (int) obj.get("hp");
        int actual_mp = (int) obj.get("mp");
        int actual_x = (int) obj.get("x");
        int actual_y = (int) obj.get("y");

        assertEquals(expect_type, actual_type);
        assertEquals(expect_id, actual_id);
        assertEquals(expect_npc_id, actual_npc_id);
        assertEquals(expect_hp, actual_hp);
        assertEquals(expect_mp, actual_mp);
        assertEquals(expect_x, actual_x);
        assertEquals(expect_y, actual_y);
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
    public void attackTest() {
        JsonPacketFactory testFactory = new JsonPacketFactory();

        final String expect_type = "attack";
        final long expect_entity_id = 1;

        JSONObject obj = testFactory.attack(expect_entity_id);

        String actual_type = (String) obj.get("type");
        long actual_entity_id = (long) obj.get("id");

        assertEquals(expect_type, actual_type);
        assertEquals(expect_entity_id, actual_entity_id);
    }

    @Test
    public void damagingTest() {
        JsonPacketFactory testFactory = new JsonPacketFactory();

        final String expect_type = "damaging";
        final long expect_entity_id = 1;
        final long expect_map_id = 3;
        final long expect_target_id = 2;
        final int expect_damage = 10;

        JSONObject obj = testFactory.damaging(expect_entity_id, expect_map_id, expect_target_id, expect_damage);

        String actual_type = (String) obj.get("type");
        long actual_entity_id = (long) obj.get("id");
        long actual_map_id = (long) obj.get("map_id");
        long actual_target_id = (long) obj.get("target_id");
        int actual_damage = (int) obj.get("damage");

        assertEquals(expect_type, actual_type);
        assertEquals(expect_entity_id, actual_entity_id);
        assertEquals(expect_map_id, actual_map_id);
        assertEquals(expect_target_id, actual_target_id);
        assertEquals(expect_damage, actual_damage);
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
