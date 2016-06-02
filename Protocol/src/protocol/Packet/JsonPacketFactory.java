package protocol.Packet;

import org.json.simple.JSONObject;

/**
 * Created by Lee on 2016-05-31.
 */
public class JsonPacketFactory implements PacketFactory {
    @Override
    public JSONObject login(String user_id, String user_pw) {
        JSONObject obj = new JSONObject();
        obj.put("type", "login");
        obj.put("user_id", user_id);
        obj.put("user_pw", user_pw);

        return obj;
    }

    @Override
    public JSONObject move(long entity_id, int src_x, int src_y, int dest_x, int dest_y) {
        JSONObject obj = new JSONObject();
        obj.put("type", "move");
        obj.put("id", entity_id);
        obj.put("src_x", src_x);
        obj.put("src_y", src_y);
        obj.put("dest_x", dest_x);
        obj.put("dest_y", dest_y);

        return obj;
    }
}
