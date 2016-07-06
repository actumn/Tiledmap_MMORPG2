package mapservice;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Created by Lee on 2016-07-06.
 */
public class MapDataLoaderTest {
    @Test
    public void test() {
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader("server/src/main/resources/map/maps.json"));
            JSONObject jsonObject = (JSONObject) obj;

            JSONArray mapArray = (JSONArray) jsonObject.get("maps");
            Iterator iterator = mapArray.iterator();
            while (iterator.hasNext()) {
                JSONObject arrayObject = (JSONObject) iterator.next();
                System.out.println(arrayObject);
            }
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        } catch (ParseException e) {
            e.printStackTrace();
            fail();
        }

    }
}
