package mapservice;

import com.game.server.mapservice.MapDataLoader;
import com.game.server.mapservice.Map;
import com.game.server.mapservice.NPCObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by Lee on 2016-07-06.
 */
public class MapDataLoaderTest {
    @Test
    public void JSONArrayTest() {
        JSONParser parser = new JSONParser();

        try {
            JSONArray mapArray = (JSONArray) parser.parse("[" +
                    "    {" +
                    "      \"id\": 0," +
                    "      \"tilewidth\":32," +
                    "      \"tileheight\":32," +
                    "      \"width\": 80," +
                    "      \"height\": 80," +
                    "      \"npcs\": [" +
                    "        {" +
                    "          \"id\": 1," +
                    "          \"x\": 34," +
                    "          \"y\": 20" +
                    "        }," +
                    "        {" +
                    "          \"id\": 2," +
                    "          \"x\": 10," +
                    "          \"y\": 10" +
                    "        }" +
                    "      ]" +
                    "    }" +
                    "]");


            assertNotNull(mapArray);

            for (Object aMapArray : mapArray) {
                JSONObject arrayObject = (JSONObject) aMapArray;
                assertNotNull(arrayObject);
            }
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
            fail();
        }

    }


    /*
    { "id": 0, "tilewidth":32, "tileheight":32,"width": 80, "height": 80, "npcs": [
        {"id": 1, "x": 34, "y": 20},
        {"id": 2, "x": 10, "y": 10}
      ]
    }
     */
    @Test
    public void mapLoadTest() {
        try {
            MapDataLoader dataLoader = new MapDataLoader();
            JSONObject mapData = (JSONObject) new JSONParser().parse(
                    "{ \"id\": 0, \"tilewidth\":32, \"tileheight\":32, \"width\": 80, \"height\": 80, \"npcs\": [" +
                    "        {\"id\": 1, \"x\": 34, \"y\": 20}," +
                    "        {\"id\": 2, \"x\": 10, \"y\": 10}" +
                    "      ]" +
                    "    }");
            assertNotNull(mapData);

            int expect_tilewidth = (int)(long) mapData.get("tilewidth");
            int expect_tileheight = (int)(long) mapData.get("tileheight");
            int expect_width = (int)(long) mapData.get("width");
            int expect_height = (int)(long) mapData.get("height");

            Map map = dataLoader.TEST_loadMapData(mapData);
            assertNotNull(map);

            assertEquals(expect_tilewidth, map.getTileWidth());
            assertEquals(expect_tileheight, map.getTileHeight());
            assertEquals(expect_width, map.getMapWidth());
            assertEquals(expect_height, map.getMapHeight());


            HashMap<Long, NPCObject> npcObjects = map.TEST_getNpcObjects();
            assertNotNull(npcObjects);
            assertEquals(2, npcObjects.size());

        } catch (ParseException e) {
            e.printStackTrace();
            fail(e.toString());
        }
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

            MapDataLoader dataLoader = new MapDataLoader();
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
            fail(e.toString());
        }
    }

    @Test
    public void npcLoadTestById() {
        long npcId = 1;
        MapDataLoader dataLoader = new MapDataLoader();
        NPCObject testNPC = dataLoader.TEST_loadNPCData(npcId);
        assertNotNull(testNPC);
    }
}
