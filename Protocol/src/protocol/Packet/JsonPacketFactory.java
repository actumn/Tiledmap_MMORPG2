package protocol.Packet;

import org.json.simple.JSONObject;

/**
 * Created by Lee on 2016-05-31.
 */
public class JsonPacketFactory implements PacketFactory {
    /*
        @return JSON like :
        {
            "type":"join"
            "user_id":"some_user_id"
            "user_pw":"some_user_pw"
            "user_name":"some_user_name"
            "job_id":1
        }
     */
    @Override
    public JSONObject join(String user_id, String user_pw, String user_name, int job_id) {
        JSONObject obj = new JSONObject();
        obj.put("type", "join");
        obj.put("user_id", user_id);
        obj.put("user_pw", user_pw);
        obj.put("user_name", user_name);
        obj.put("job_id", job_id);

        return obj;
    }

    /*
            @return JSON like :
            {
                "type":"login"
                "user_id":"some_user_id"
                "user_pw":"some_user_pw"
            }
         */
    @Override
    public JSONObject login(String user_id, String user_pw) {
        JSONObject obj = new JSONObject();
        obj.put("type", "login");
        obj.put("user_id", user_id);
        obj.put("user_pw", user_pw);

        return obj;
    }

    /*
        @return JSON like :
        {
            "type":"character"
            "id":uuid
            "name":"character_name"
            "level":level
            "job_id":job_id
        }
     */
    @Override
    public JSONObject character(long entity_id, String name, int level, int job_id) {
        JSONObject obj = new JSONObject();
        obj.put("type","character");
        obj.put("id", entity_id);
        obj.put("name", name);
        obj.put("level", level);
        obj.put("job_id", job_id);

        return obj;
    }

    /*
        @return JSON like :
        {
            "type":"move"
            "id":entity_id
            "dest_map_id":dest__map_id
            "dest_x":dest_x
            "dest_y":dest_y
        }
     */
    @Override
    public JSONObject move(long entity_id, int map_id, int dest_x, int dest_y) {
        JSONObject obj = new JSONObject();
        obj.put("type", "move");
        obj.put("id", entity_id);
        obj.put("dest_map_id", map_id);
        obj.put("dest_x", dest_x);
        obj.put("dest_y", dest_y);

        return obj;
    }


    /*
        @return JSON like :
        {
            "type":"chat"
            "content":"Hello"
        }
     */
    @Override
    public JSONObject chat(String content) {
        JSONObject obj = new JSONObject();
        obj.put("type", "chat");
        obj.put("content", content);

        return obj;
    }

    /*
        @return JSON like :
        {
            "type":"notify"
            "content":"some notify from server"
        }
     */
    @Override
    public JSONObject notify(String content) {
        JSONObject obj = new JSONObject();
        obj.put("type", "notify");
        obj.put("content", content);

        return obj;
    }
}
