package com.game.server;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

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
}
