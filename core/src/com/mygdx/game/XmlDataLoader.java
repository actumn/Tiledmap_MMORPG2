package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.mygdx.game.map.Map;
import com.mygdx.game.map.MapMoveDestination;
import com.mygdx.game.map.MapMovePoint;
import com.mygdx.game.object.Effect;
import com.mygdx.game.object.NPC;
import com.mygdx.game.object.Player;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Lee on 2016-06-01.
 */
public class XmlDataLoader {
    private static XmlDataLoader instance = new XmlDataLoader();
    public static XmlDataLoader getInstance() { return instance; }

    private XmlReader reader = new XmlReader();

    private FileHandle mapHandle = Gdx.files.internal("data/xml/maps.xml");
    private FileHandle npcHandle = Gdx.files.internal("data/xml/npcs.xml");
    private FileHandle sheetHandle = Gdx.files.internal("data/xml/sheets.xml");
    private FileHandle jobHandle = Gdx.files.internal("data/xml/jobs.xml");


    public Map loadMap(int mapId) throws IOException {
        XmlReader.Element maps = reader.parse(mapHandle);
        Array<XmlReader.Element> mapArray = maps.getChildrenByName("map");
        Map map = null;

        for (XmlReader.Element mapData: mapArray) {
            if (Integer.parseInt(mapData.getAttribute("id")) != mapId) continue;

            String mapName = mapData.getAttribute("name");
            String mapFileName = mapData.getAttribute("filename");

            map = new Map(mapName, mapFileName)
                        .mapId(mapId);

            XmlReader.Element moves = mapData.getChildByName("moves");
            Array<XmlReader.Element> moveArray = moves.getChildrenByName("move");
            for (XmlReader.Element move: moveArray) {
                int x = Integer.parseInt(move.getAttribute("x"));
                int y = Integer.parseInt(move.getAttribute("y"));
                int hashCoefficient = map.getTilesWidth();
                int newMap = Integer.parseInt(move.getAttribute("new_map"));
                int newX = Integer.parseInt(move.getAttribute("new_x"));
                int newY = Integer.parseInt(move.getAttribute("new_y"));

                map.addMovePoint(new MapMovePoint(x, y, hashCoefficient), new MapMoveDestination(newMap, newX, newY));
            }


            XmlReader.Element npcs = mapData.getChildByName("npcs");
            Array<XmlReader.Element> npcArray = npcs.getChildrenByName("npc");

            for (XmlReader.Element npcData: npcArray) {
                int npcId = Integer.parseInt(npcData.getAttribute("id"));
                int npcX = Integer.parseInt(npcData.getAttribute("x"));
                int npcY = Integer.parseInt(npcData.getAttribute("y"));
                int direction = Integer.parseInt(npcData.getAttribute("direction"));

                NPC npc = loadNPC(npcId)
                        .setMap(map)
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

            String name = npcData.get("name");
            int team = Integer.parseInt(npcData.get("team"));
            int hp = Integer.parseInt(npcData.get("hp"));
            int mp = Integer.parseInt(npcData.get("mp"));
            int vision = Integer.parseInt(npcData.get("vision"));
            int level = Integer.parseInt(npcData.get("level"));
            int dropExp = Integer.parseInt(npcData.get("drop_exp"));
            int dropGold = Integer.parseInt(npcData.get("drop_gold"));
            int atk = Integer.parseInt(npcData.get("atk"));
            int def = Integer.parseInt(npcData.get("def"));

            npc = new NPC()
                    .team(team)
                    .setName(name)
                    .stat(hp, mp, atk, def)
                    .dropExpGold(dropExp, dropGold)
                    .level(level)
                    .vision(vision);

            XmlReader.Element sprites = npcData.getChildByName("sprites");
            Array<XmlReader.Element> spriteArray = sprites.getChildrenByName("sprite");
            for (XmlReader.Element spriteData: spriteArray) {
                int stateValue = Integer.parseInt(spriteData.getAttribute("state"));
                String key = spriteData.getAttribute("key");
                int iIndex = Integer.parseInt(spriteData.getAttribute("i_index"));
                int jIndex = Integer.parseInt(spriteData.getAttribute("j_index"));

                npc.loadAnimation(stateValue, key, iIndex, jIndex);
            }

            break;
        }

        return npc;
    }

    public Player loadPlayer(int jobId) throws IOException {
        XmlReader.Element jobs = reader.parse(jobHandle);
        Array<XmlReader.Element> jobArray = jobs.getChildrenByName("job");

        Player player = null;

        for(XmlReader.Element jobData: jobArray) {
            if (Integer.parseInt(jobData.getAttribute("id")) != jobId) continue;

            String jobName = jobData.get("name");
            int lvhp = Integer.parseInt(jobData.get("lvhp"));
            int lvmp = Integer.parseInt(jobData.get("lvmp"));
            int lvatk = Integer.parseInt(jobData.get("lvatk"));
            int lvdef = Integer.parseInt(jobData.get("lvdef"));

            player = new Player().loadJob(jobName, lvhp, lvmp, lvatk, lvdef);


            XmlReader.Element sprites = jobData.getChildByName("sprites");
            Array<XmlReader.Element> spriteArray = sprites.getChildrenByName("sprite");
            for (XmlReader.Element spriteData: spriteArray) {
                int stateValue = Integer.parseInt(spriteData.getAttribute("state"));
                String key = spriteData.getAttribute("key");

                player.loadAnimation(stateValue, key);
            }


            break;
        }
        return player;
    }


    public Effect loadEffect(int id) throws IOException {

        return null;
    }

    public HashMap<String, Texture> loadSheets() throws IOException{
        HashMap<String,Texture> sheetMap = new HashMap<>();


        XmlReader.Element sheets = reader.parse(sheetHandle);

        Array<XmlReader.Element> sheetArray = sheets.getChildrenByName("sheet");

        for(XmlReader.Element sheet: sheetArray) {
            String key = sheet.getAttribute("key");
            String filepath = sheet.getAttribute("filepath");

            sheetMap.put(key, new Texture(Gdx.files.internal(filepath)));
        }

        return sheetMap;
    }
}