package com.game.server.mapservice;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


/**
 * Created by Lee on 2016-06-19.
 */
public class JsonDataLoader {
    private JSONParser parser;
    public JsonDataLoader() {
        this.parser = new JSONParser();

    }


    public HashMap<Long, Map> loadMaps(MapService mapService) {
        HashMap<Long, Map> maps = new HashMap<>();
        JSONObject mapsData = null;
        try {
            mapsData = (JSONObject) parser.parse(new FileReader("map/maps.json"));
            JSONArray mapArray = (JSONArray) mapsData.get("maps");

            for (Object mapObject : mapArray) {
                JSONObject mapData = (JSONObject) mapObject;
                Map newMap = loadMapData(mapData)
                        .mapService(mapService);
                maps.put(newMap.getMapId(), newMap);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return maps;
    }

    /*
    {
      "id": 0,
      "width": 80,
      "height": 80,
      "npcs": [
        {
          "id": 1,
          "x": 34,
          "y": 20
        },
        {
          "id": 2,
          "x": 10,
          "y": 10
        }
      ]
    }
     */
    private Map loadMapData(JSONObject mapObject) {
        long id = (long) mapObject.get("id");
        int width = (int)(long) mapObject.get("width");
        int height = (int)(long) mapObject.get("height");
        JSONArray npcArray = (JSONArray) mapObject.get("npcs");

        /* create new map */
        Map map = new Map()
                .mapId(id)
                .size(width, height);

        /* load npcs */
        for (Object npcData : npcArray) {
            JSONObject npcObject = (JSONObject) npcData;
            long npcId = (long) npcObject.get("id");
            int x = (int)(long) npcObject.get("x"); int y = (int)(long) npcObject.get("y");
            map.addNPC(loadNPCData((long) npcObject.get("id")).map(map).position(x, y));
        }

        return map;
    }

    /*
    {
  "npcs": [
    {
      "id":0,
      "name":"test",
      "team":0,
      "level":0,
      "vision":0,
      "hp":0,
      "mp":0,
      "drop_exp":0,
      "drop_gold":0,
      "atk":0,
      "def":0,
      "drop_items":[]
    }
    ]
    }
     */
    private NPCObject loadNPCData(long id) {
        NPCObject npc = null;
        try {
            JSONObject npcsData = (JSONObject) parser.parse(new FileReader("map/npcs.json"));
            JSONArray npcArray = (JSONArray) npcsData.get("npcs");

            for(Object npcObject : npcArray) {
                JSONObject npcData = (JSONObject) npcObject;
                long npcId = (long) npcData.get("id");
                if (npcId != id) continue;

                npc = loadNPCData(npcData);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return npc;
    }
    private NPCObject loadNPCData(JSONObject npcData) {
        long npcId = (long) npcData.get("id");
        String name = (String) npcData.get("name");
        int team = (int)(long) npcData.get("team");
        int level = (int)(long) npcData.get("level");
        int vision = (int)(long) npcData.get("vision");
        int hp = (int)(long) npcData.get("hp");
        int mp = (int)(long) npcData.get("mp");
        int atk = (int)(long) npcData.get("atk");
        int def= (int)(long) npcData.get("def");
        int dropExp = (int)(long) npcData.get("drop_exp");
        int dropGold = (int)(long) npcData.get("drop_gold");

        JSONArray drop_items = (JSONArray) npcData.get("drop_items");


        NPCObject npc = new NPCObject()
                .npcId(npcId)
                .team(team)
                .status(level, vision, hp, mp, atk, def)
                .reward(dropExp, dropGold);

        return npc;
    }

    public Map TEST_loadMapData(JSONObject mapObject) {
        return loadMapData(mapObject);
    }
    public NPCObject TEST_loadNPCData(JSONObject npcObjectData) {
        return loadNPCData(npcObjectData);
    }
}
