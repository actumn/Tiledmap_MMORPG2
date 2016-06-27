package com.game.server.mapservice;

import com.game.server.mapservice.Map;
import com.game.server.mapservice.MapService;
import com.game.server.mapservice.NPCObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.util.HashMap;

/**
 * Created by Lee on 2016-06-19.
 */
public class XmlDataLoader {
    public XmlDataLoader() {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();



        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public HashMap<Integer, Map> loadMaps(MapService mapService) {

        return null;
    }

    private Map loadMapData() {
        return null;
    }

    private NPCObject loadMobObject() {
        return null;
    }
}
