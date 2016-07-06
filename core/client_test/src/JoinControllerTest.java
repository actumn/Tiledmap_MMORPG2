package src;

import com.mygdx.game.scene.JoinScene;
import io.netty.channel.embedded.EmbeddedChannel;
import network.Network;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.Test;
import protocol.Packet.PacketFactory;

import static org.junit.Assert.*;

/**
 * Created by Lee on 2016-06-08.
 */
public class JoinControllerTest {
    @Test
    public void joinTest() {
        JoinScene joinScene = new JoinScene();
        EmbeddedChannel testChannel = new EmbeddedChannel();
        Network.getInstance().TEST_setChannel(testChannel);
        PacketFactory packetFactory = Network.getInstance().getPacketFactory();

        assertNotNull(Network.getInstance());
        assertNotNull(Network.getInstance().TEST_getChannel());
        assertEquals(testChannel, Network.getInstance().TEST_getChannel());

        String expect_type = "join";
        int expect_job_id = 1;
        String expect_user_id = "test";
        String expect_user_name = "test";
        String expect_user_pw = "test";


        Network.getInstance().addPacket(packetFactory.notify("회원가입 성공"));

        String joinRes = joinScene.join(1,"test", "test", "test");
        assertEquals("회원가입 성공", joinRes);
        String packetData = (String) testChannel.readOutbound();
        JSONObject packet = (JSONObject) JSONValue.parse(packetData);
        assertNotNull(packet);

        String actual_type = (String) packet.get("type");
        int actual_job_id = (int)(long) packet.get("job_id");
        String actual_user_id = (String) packet.get("user_id");
        String actual_user_name = (String) packet.get("user_name");
        String actual_user_pw = (String) packet.get("user_pw");

        assertEquals(expect_type, actual_type);
        assertEquals(expect_job_id, actual_job_id);
        assertEquals(expect_user_id, actual_user_id);
        assertEquals(expect_user_name, actual_user_name);
        assertEquals(expect_user_pw, actual_user_pw);

        testChannel.finish();
    }
}
