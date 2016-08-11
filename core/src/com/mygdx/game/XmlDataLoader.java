package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.mygdx.game.map.Map;
import com.mygdx.game.map.MapMoveDestination;
import com.mygdx.game.map.MapMovePoint;
import com.mygdx.game.object.Effect;
import com.mygdx.game.object.NPC;
import com.mygdx.game.object.Player;
import com.mygdx.game.ui.graphics.EntitySheet;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Lee on 2016-06-01.
 */
public class XmlDataLoader {
    private XmlReader reader = new XmlReader();

    private FileHandle mapHandle;
    private FileHandle npcHandle;
    private FileHandle sheetHandle;
    private FileHandle jobHandle;
    private FileHandle expHandle;

    public XmlDataLoader() {
        this.mapHandle = new FileHandle("data/xml/maps.xml");
        this.npcHandle = new FileHandle("data/xml/npcs.xml");
        this.sheetHandle = new FileHandle("data/xml/sheets.xml");
        this.jobHandle = new FileHandle("data/xml/jobs.xml");
        this.expHandle = new FileHandle("data/xml/exps.xml");
    }


    public Map loadMap(long mapId) throws IOException {
        XmlReader.Element maps = reader.parse(mapHandle);
        Array<XmlReader.Element> mapArray = maps.getChildrenByName("map");
        Map map = null;

        for (XmlReader.Element mapData: mapArray) {
            if (Long.parseLong(mapData.getAttribute("id")) != mapId) continue;

            String mapName = mapData.getAttribute("name");
            String mapFileName = mapData.getAttribute("filename");

            map = new Map(mapName, mapFileName)
                        .mapId(mapId);

            XmlReader.Element moves = mapData.getChildByName("moves");
            Array<XmlReader.Element> moveArray = moves.getChildrenByName("move");
            for (XmlReader.Element move: moveArray) {
                int x = Integer.parseInt(move.getAttribute("x"));
                int y = Integer.parseInt(move.getAttribute("y"));

                int newMap = Integer.parseInt(move.getAttribute("new_map"));
                int newX = Integer.parseInt(move.getAttribute("new_x"));
                int newY = Integer.parseInt(move.getAttribute("new_y"));

                map.addMovePoint(new MapMovePoint(x, y), new MapMoveDestination(newMap, newX, newY));
            }


            XmlReader.Element npcs = mapData.getChildByName("npcs");
            Array<XmlReader.Element> npcArray = npcs.getChildrenByName("npc");

            long uniqueId = -1;
            for (XmlReader.Element npcData: npcArray) {
                long npcId = Long.parseLong(npcData.getAttribute("id"));
                int npcX = Integer.parseInt(npcData.getAttribute("x"));
                int npcY = Integer.parseInt(npcData.getAttribute("y"));
                int direction = Integer.parseInt(npcData.getAttribute("direction"));

                NPC npc = loadNPC(npcId)
                        .entityId(--uniqueId)
                        .setMap(map)
                        .position(map.getTilePosX(npcX), map.getTilePosY(npcY))
                        .direction(direction);

                map.add(npc);
            }

            break;
        }
        
        return map;
    }


    public NPC loadNPC(long npcId) throws IOException {
        XmlReader.Element npcs = reader.parse(npcHandle);
        Array<XmlReader.Element> npcArray = npcs.getChildrenByName("npc");

        NPC npc = null;
        for (XmlReader.Element npcData: npcArray) {
            if (Long.parseLong(npcData.getAttribute("id")) != npcId) continue;

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

        for (XmlReader.Element jobData: jobArray) {
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
    public int loadMaxExp(int level){
        XmlReader.Element exps = null;
        try {
            exps = reader.parse(expHandle);
        } catch (IOException e) {
            e.printStackTrace();
            return 999999;
        }
        Array<XmlReader.Element> expArray = exps.getChildrenByName("exp");

        int maxExp = 999999;
        for (XmlReader.Element expData: expArray) {
            if (Integer.parseInt(expData.getAttribute("level")) != level) continue;

            maxExp = Integer.parseInt(expData.getText());
        }
        return maxExp;
    }


    public HashMap<String, EntitySheet> loadSheets() throws IOException{
        HashMap<String,EntitySheet> sheetMap = new HashMap<>();


        XmlReader.Element sheets = reader.parse(sheetHandle);

        Array<XmlReader.Element> sheetArray = sheets.getChildrenByName("sheet");

        for(XmlReader.Element sheet: sheetArray) {
            String key = sheet.getAttribute("key");
            String filepath = sheet.getAttribute("filepath");
            int horizonCount = Integer.parseInt(sheet.getAttribute("horizon"));
            int verticalCount = Integer.parseInt(sheet.getAttribute("vertical"));

            sheetMap.put(key, new EntitySheet(Gdx.files.internal(filepath)).charactersCount(horizonCount, verticalCount));
        }

        return sheetMap;
    }


}