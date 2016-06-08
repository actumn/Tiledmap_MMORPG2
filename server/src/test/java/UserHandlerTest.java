import com.game.server.db.DBManager;
import com.game.server.userservice.UserHandler;
import com.game.server.userservice.UserService;
import io.netty.channel.embedded.EmbeddedChannel;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.Test;
import protocol.Packet.JsonPacketFactory;

import java.security.MessageDigest;

import static org.junit.Assert.*;
/**
 * Created by Lee on 2016-06-02.
 */
public class UserHandlerTest {
    @Test
    public void channelResponseTest() {
        /* TEST Channel */
        UserService testUserService = new UserService(6112);
        EmbeddedChannel testChannel = new EmbeddedChannel(new UserHandler(testUserService));

        /* JSON Packet Factory */
        JsonPacketFactory packetFactory = new JsonPacketFactory();

        JSONObject joinReq = packetFactory.join("admin2", "1234", "홍길동", 1);
        testChannel.writeInbound(joinReq.toJSONString());

        String joinResData = (String) testChannel.readOutbound();
        JSONObject joinRes = (JSONObject) JSONValue.parse(joinResData);
        assertNotNull(joinRes);
        assertEquals("{type\":\"notify\",\"content\":\"회원가입 실패\"}", joinRes.toJSONString());


        JSONObject loginReq = packetFactory.login("admin", "1234");
        testChannel.writeInbound(loginReq.toJSONString());

        String loginResData = (String) testChannel.readOutbound();
        JSONObject loginRes = (JSONObject) JSONValue.parse(loginResData);
        assertNotNull(loginRes);
        assertEquals("{type\":\"notify\",\"content\":\"회원가입 실패\"}", joinRes.toJSONString());

        testChannel.finish();
    }


    @Test
    public void StringToJSONTest() {
        JSONObject obj = new JSONObject();
        obj.put("type", "test");
        assertNotNull(obj);

        String jsonString = obj.toJSONString();
        assertEquals("{\"type\":\"test\"}", jsonString);

        JSONObject response = UserHandler.stringToJson(jsonString);
        assertEquals(obj, response);
    }
}
