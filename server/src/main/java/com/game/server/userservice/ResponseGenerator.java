package com.game.server.userservice;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Created by Lee on 2016-06-02.
 */
public class ResponseGenerator {





    public static JSONObject stringToJson(String s) {
        return (JSONObject) JSONValue.parse(s);
    }
}
