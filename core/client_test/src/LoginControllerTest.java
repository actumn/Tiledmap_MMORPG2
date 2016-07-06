package src;

import com.mygdx.game.scene.LoginScene;
import io.netty.channel.embedded.EmbeddedChannel;
import network.Network;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.Test;
import protocol.Packet.PacketFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Lee on 2016-06-08.
 */
public class LoginControllerTest {
    @Test
    public void loginTest() {
        LoginScene loginScene = new LoginScene();
        EmbeddedChannel testChannel = new EmbeddedChannel();
        Network.getInstance().TEST_setChannel(testChannel);
        PacketFactory packetFactory = Network.getInstance().getPacketFactory();

        assertNotNull(Network.getInstance());
        assertNotNull(Network.getInstance().TEST_getChannel());
        assertEquals(testChannel, Network.getInstance().TEST_getChannel());

        String expect_user_id = "test";
        String expect_user_pw = "test";

        Network.getInstance().addPacket(packetFactory.notify("로그인 성공"));

        String joinRes = loginScene.login("test", "test");
        assertEquals("로그인 성공", joinRes);
        String packetData = (String) testChannel.readOutbound();
        JSONObject packet = (JSONObject) JSONValue.parse(packetData);
        assertNotNull(packet);

        String actual_user_id = (String) packet.get("user_id");
        String actual_user_pw = (String) packet.get("user_pw");

        assertEquals(expect_user_id, actual_user_id);
        assertEquals(expect_user_pw, actual_user_pw);
    }

}
