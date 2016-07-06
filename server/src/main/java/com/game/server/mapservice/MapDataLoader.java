package com.game.server.mapservice;

import com.game.server.mapservice.Map;
import com.game.server.mapservice.MapService;
import com.game.server.mapservice.NPCObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.util.HashMap;

/**
 * Created by Lee on 2016-06-19.
 */
public class MapDataLoader {
    private JSONParser parser;
    public MapDataLoader() {
        this.parser = new JSONParser();

    }

    public HashMap<Integer, Map> loadMaps(MapService mapService) {

        return null;
    }

    private Map loadMapData(JSONObject mapObject) {
        return null;
    }

    private NPCObject loadMobObject() {
        return null;
    }

    public Map TEST_loadMapData(JSONObject mapObject) {
        return loadMapData(mapObject);
    }
}
