import com.game.server.userservice.UserHandler;
import org.json.simple.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by Lee on 2016-06-02.
 */
public class UserHandlerTest {
    @Test
    public void channelResponseTest() {
        JSONObject request = new JSONObject();
        request.put("type", "login");
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
