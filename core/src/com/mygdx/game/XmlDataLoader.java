package com.mygdx.game;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.mygdx.game.map.Map;
import com.mygdx.game.map.MapMoveDestination;
import com.mygdx.game.map.MapMovePoint;
import com.mygdx.game.object.Effect;
import com.mygdx.game.object.Entity;
import com.mygdx.game.object.EntityAnimation;
import com.mygdx.game.object.NPC;

import java.io.IOException;

/**
 * Created by Lee on 2016-06-01.
 */
public class XmlDataLoader {
    private static XmlDataLoader instance = new XmlDataLoader();
    public static XmlDataLoader getInstance() { return instance; }

    private XmlReader reader = new XmlReader();

    private FileHandle mapHandle = new FileHandle("data/xml/maps.xml");
    private FileHandle npcHandle = new FileHandle("data/xml/npcs.xml");
    private FileHandle spritesHandle = new FileHandle("data/xml/sprites.xml");


    public Map loadMap(int mapId) throws IOException {
        XmlReader.Element maps = reader.parse(mapHandle);
        Array<XmlReader.Element> mapArray = maps.getChildrenByName("map");
        Map map = null;

        for (XmlReader.Element mapData: mapArray) {
            if (Integer.parseInt(mapData.getAttribute("id")) != mapId) continue;

            String mapName = mapData.getAttribute("name");
            String mapFileName = mapData.getAttribute("filename");

            map = new Map(mapName, mapFileName);

            Array<XmlReader.Element> moveArray = mapData.getChildrenByName("moves");

            for (XmlReader.Element move: moveArray) {
                int x = Integer.parseInt(move.getAttribute("x")); int y = Integer.parseInt(move.getAttribute("y"));
                int hashCoefficient = map.getTilesWidth();
                int newMap = Integer.parseInt(move.getAttribute("new_map"));
                int newX = Integer.parseInt(move.getAttribute("new_x"));
                int newY = Integer.parseInt(move.getAttribute("new_y"));

                map.addMovePoint(new MapMovePoint(x, y, hashCoefficient), new MapMoveDestination(newMap, newX, newY));
            }

            Array<XmlReader.Element> npcs = mapData.getChildrenByName("npcs");

            for (XmlReader.Element npcData: npcs) {
                int npcId = Integer.parseInt(npcData.getAttribute("id"));
                int npcX = Integer.parseInt(npcData.getAttribute("x"));
                int npcY = Integer.parseInt(npcData.getAttribute("y"));
                int direction = Integer.parseInt(npcData.getAttribute("direction"));

                NPC npc = loadNPC(npcId)
                        .xy(map.getTilePosX(npcX), map.getTilePosY(npcY))
                        .direction(direction);

                map.add(npc);
            }

            break;
        }
        
        return map;
    }
    public NPC loadNPC(int npcId) throws IOException {
        XmlReader.Element npcs = reader.parse(npcHandle);
        Array<XmlReader.Element> npcArray = npcs.getChildrenByName("npc");

        NPC npc = null;
        for (XmlReader.Element npcData: npcArray) {
            if (Integer.parseInt(npcData.getAttribute("id")) != npcId) continue;

            String name = npcData.getChildByName("name").getText();
            int team = Integer.parseInt(npcData.getChildByName("team").getText());
            int hp = Integer.parseInt(npcData.getChildByName("hp").getText());
            int mp = Integer.parseInt(npcData.getChildByName("mp").getText());
            int spritesId = Integer.parseInt(npcData.getChildByName("sprites").getText());
            int vision = Integer.parseInt(npcData.getChildByName("vision").getText());
            int level = Integer.parseInt(npcData.getChildByName("level").getText());
            int dropExp = Integer.parseInt(npcData.getChildByName("drop_exp").getText());

        }
        return npc;
    }
    /*
    <npc id='1'>
        <name>behemoth</name>
        <team>1</team>
        <hp>20</hp>
        <mp>0</mp>
        <sprites>1</sprites>
        <tps>1500</tps>
        <vision>5</vision>
        <level>1</level>
        <drop_exp>4</drop_exp>
        <drop_gold>10</drop_gold>
        <regen>10000</regen>
        <atk>8</atk>
        <def>0</def>
        <hit>100</hit>
        <avo>0</avo>
        <drop_items>

        </drop_items>
    </npc>
     */

    public Character loadCharacter() {
        return null;
    }
    public EntityAnimation loadCharacterAnimation(Entity entity, int id) throws IOException {

        return null;
    }
    public EntityAnimation loadNPCAnimation() {

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