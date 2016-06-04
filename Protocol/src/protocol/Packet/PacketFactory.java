package protocol.Packet;

import org.json.simple.JSONObject;

/**
 * Created by Lee on 2016-05-31.
 */
public interface PacketFactory {
    public JSONObject login(String user_id, String user_pw);
    public JSONObject character(long uuid, String name, int level, int job_id);
    public JSONObject move(long entity_id, int map_id, int dest_x, int dest_y);
    public JSONObject chat(String content);
    public JSONObject notify(String content);
}
