package mapservice;

import com.game.server.mapservice.Map;
import com.game.server.mapservice.NPCObject;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Lee on 2016-07-07.
 */
public class NPCObjectTest {
    @Test
    public void initTest() {
        /*
        {
      "id":1,
      "name":"베이비드래곤",
      "team":1,
      "level":1,
      "vision":5,
      "hp":100,
      "mp":0,
      "drop_exp":4,
      "drop_gold":10,
      "atk":8,
      "def":0,
      "drop_items":[]
    }
         */
        long npcId = 1;
        int team = 1;
        int level = 1;
        int vision = 5;
        int hp = 100;
        int mp = 0;
        int drop_exp = 4;
        int drop_gold = 10;
        int atk = 8;
        int def = 0;

        NPCObject npcObject = new NPCObject()
                .npcId(npcId)
                .team(team)
                .status(level, vision, hp, mp, atk, def)
                .reward(drop_exp, drop_gold);

        assertEquals(npcId, npcObject.getNpcId());
        assertEquals(team, npcObject.getTeam());
        assertEquals(vision, npcObject.getVision());
        assertEquals(hp, npcObject.getMaxHp());
        assertEquals(npcObject.getHp(), npcObject.getMaxHp());
        assertEquals(mp, npcObject.getMaxMp());
        assertEquals(npcObject.getMp(), npcObject.getMaxMp());
        assertEquals(atk, npcObject.getAtk());
        assertEquals(def, npcObject.getDef());
    }

    @Test
    public void setMapTest() {
        Map map = new Map().
                mapId(1);

        NPCObject npcObject = new NPCObject().map(map);

        assertEquals(map, npcObject.getMap());
        assertEquals(map.getMapId(), npcObject.getMapId());
    }
}
