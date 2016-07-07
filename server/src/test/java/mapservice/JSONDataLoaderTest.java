package mapservice;

import com.game.server.mapservice.JsonDataLoader;
import com.game.server.mapservice.NPCObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Lee on 2016-07-06.
 */
public class JsonDataLoaderTest {
    @Test
    public void JSONArrayTest() {
        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse("{\n" +
                    "  \"maps\": [\n" +
                    "    {\n" +
                    "      \"id\": 0,\n" +
                    "      \"width\": 80,\n" +
                    "      \"height\": 80,\n" +
                    "      \"npcs\": [\n" +
                    "        {\n" +
                    "          \"id\": 1,\n" +
                    "          \"x\": 34,\n" +
                    "          \"y\": 20\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"id\": 2,\n" +
                    "          \"x\": 10,\n" +
                    "          \"y\": 10\n" +
                    "        }\n" +
                    "      ]\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}");

            assertNotNull(jsonObject);

            JSONArray mapArray = (JSONArray) jsonObject.get("maps");
            assertNotNull(mapArray);

            for (Object aMapArray : mapArray) {
                JSONObject arrayObject = (JSONObject) aMapArray;
                assertNotNull(arrayObject);
                System.out.println(arrayObject);
            }
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
            fail();
        }

    }


    @Test
    public void mapLoadTest() {
        JsonDataLoader dataLoader = new JsonDataLoader();

    }
    /*
    {"id":1, "name":"베이비드래곤", "team":1, "level":1, "vision":5, "hp":100, "mp":0, "drop_exp":4, "drop_gold":10, "atk":8, "def":0, "drop_items":[] }
     */
    @Test
    public void npcLoadTest() {
        try {
            JSONObject npcData = (JSONObject) new JSONParser().parse(
                    "{\"id\":1, \"name\":\"베이비드래곤\", \"team\":1, \"level\":1, \"vision\":5, \"hp\":100, \"mp\":0, \"drop_exp\":4, \"drop_gold\":10, \"atk\":8, \"def\":0, \"drop_items\":[] }"
            );
            assertNotNull(npcData);

            long expect_id = (long) npcData.get("id");
            int expect_team = (int)(long) npcData.get("team");
            int expect_level = (int)(long) npcData.get("level");
            int expect_vision = (int)(long) npcData.get("vision");
            int expect_hp = (int)(long) npcData.get("hp");
            int expect_mp = (int)(long) npcData.get("mp");
            int expect_drop_exp = (int)(long) npcData.get("drop_exp");
            int expect_drop_gold = (int)(long) npcData.get("drop_gold");
            int expect_atk = (int)(long) npcData.get("atk");
            int expect_def = (int)(long) npcData.get("def");

            JSONArray drop_items = (JSONArray) npcData.get("drop_items");
            assertNotNull(drop_items);

            JsonDataLoader dataLoader = new JsonDataLoader();
            NPCObject npc = dataLoader.TEST_loadNPCData(npcData);
            assertNotNull(npc);


            assertEquals(expect_id, npc.getNpcId());
            assertEquals(expect_team, npc.getTeam());
            assertEquals(expect_level, npc.getLevel());
            assertEquals(expect_vision, npc.getVision());
            assertEquals(expect_hp, npc.getMaxHp());
            assertEquals(expect_mp, npc.getMaxMp());
            assertEquals(expect_drop_exp, npc.getDropExp());
            assertEquals(expect_drop_gold, npc.getDropGold());
            assertEquals(expect_atk, npc.getAtk());
            assertEquals(expect_def, npc.getDef());

        } catch (ParseException e) {
            e.printStackTrace();
            fail();
        }
    }
}
