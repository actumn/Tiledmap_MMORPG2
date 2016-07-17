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
        {
            "type":"npc",
            "id":entityId,
            "npc_id": npcId,
            "hp": hp,
            "mp": mp,
            "x": x,
            "y": y
        }
     */
    @Override
    public JSONObject npc(long entityId, long npcId, int hp, int mp, int x, int y) {
        JSONObject obj = new JSONObject();
        obj.put("type", "npc");
        obj.put("id", entityId);
        obj.put("npc_id", npcId);
        obj.put("hp", hp);
        obj.put("mp", mp);
        obj.put("x", x);
        obj.put("y", y);

        return obj;
    }

    @Override
    public JSONObject entityUpdate(long entityId, int entityHp) {
        JSONObject obj = new JSONObject();
        obj.put("type", "update");
        obj.put("id", entityId);
        obj.put("entity_hp", entityHp);

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
    public JSONObject move(long entity_id, long map_id, int dest_x, int dest_y) {
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
            "id":entity_id
            "content":"Hello"
        }
     */
    @Override
    public JSONObject chat(long entity_id, String content) {
        JSONObject obj = new JSONObject();
        obj.put("type", "chat");
        obj.put("id", entity_id);
        obj.put("content", content);

        return obj;
    }

    /*
        {
            "type":"attack"
            "id":entity_id
        }
     */

    @Override
    public JSONObject attack(long entityId) {
        JSONObject obj = new JSONObject();

        obj.put("type", "attack");
        obj.put("id", entityId);
        return obj;
    }

    /*
            {
                "type":"damaging"
                "id":entity_id
                "map_id": map_id
                "target_id": target_id
                "damage": damage
            }
         */
    @Override
    public JSONObject damaging(long entity_id, long map_id, long target_id, int damage) {
        JSONObject obj = new JSONObject();

        obj.put("type", "damaging");
        obj.put("id", entity_id);
        obj.put("map_id", map_id);
        obj.put("target_id", target_id);
        obj.put("damage", damage);
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
