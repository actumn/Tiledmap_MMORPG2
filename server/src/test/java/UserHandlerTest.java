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
}
