package com.mygdx.game;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.mygdx.game.map.Map;
import com.mygdx.game.object.Effect;
import com.mygdx.game.object.Entity;
import com.mygdx.game.object.EntityAnimation;

import java.io.IOException;

/**
 * Created by Lee on 2016-06-01.
 */
public class XmlDataLoader {
    private static XmlDataLoader instance = new XmlDataLoader();
    public static XmlDataLoader getInstance() { return instance; }

    private XmlReader reader = new XmlReader();

    private FileHandle mapHandle = new FileHandle("maps.xml");
    private FileHandle npcHandle = new FileHandle("npcs.xml");
    private FileHandle spritesHandle = new FileHandle("sprites.xml");


    public Map loadMap(int id) throws IOException {
        XmlReader.Element maps = reader.parse(mapHandle);
        Array<XmlReader.Element> mapArray = maps.getChildrenByName("map");
        
        return null;
    }
    public EntityAnimation loadCharacterAnimation(Entity entity, int id) throws IOException {


        return null;
    }
    public Effect loadEffect(int id) throws IOException {

        return null;
    }
}

/*
		XmlReader reader = new XmlReader();
		FileHandle fileHandle = new FileHandle("Test.xml");
		try {
			XmlReader.Element root = reader.parse(fileHandle);
			Array<XmlReader.Element> students = root.getChildrenByName("student");
			for (XmlReader.Element child : students) {
				System.out.println(child.getAttribute("id"));
				System.out.println(child.getChildByName("num").getText());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
 */