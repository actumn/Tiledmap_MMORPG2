package protocol.Packet;

import org.json.simple.JSONObject;

/**
 * Created by Lee on 2016-05-31.
 */
public interface PacketFactory {
    public JSONObject login(String user_id, String user_pw);
    public JSONObject move(long entity_id, int src_x, int src_y, int dest_x, int dest_y);
}
