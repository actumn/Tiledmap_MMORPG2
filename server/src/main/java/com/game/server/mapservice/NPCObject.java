package com.game.server.mapservice;

import com.game.server.Server;

/**
 * Created by Lee on 2016-06-15.
 */
public class NPCObject {
    private long entityId;
    private long mapId;
    private Map map;

    private long npcId;
    private int team;
    private int x, y;
    private int level;
    private int vision;
    private int hp, maxHp;
    private int mp, maxMp;
    private int atk, def;
    private int dropGold, dropExp;

    private boolean dead;

    public NPCObject() {
        entityId = Server.uniqueId+=1;
        dead = false;
    }
    public NPCObject team(int team) {
        this.team = team;
        return this;
    }
    public NPCObject npcId(long npcId) {
        this.npcId = npcId;
        return this;
    }
    public NPCObject position(int x, int y) {
        this.x = x; this.y = y;
        return this;
    }
    public NPCObject map(Map map) {
        this.map = map; this.mapId = map.getMapId();
        return this;
    }
    public NPCObject status(int level, int vision, int hp, int mp, int atk, int def) {
        this.level = level; this.vision = vision;
        this.hp = this.maxHp = hp; this.mp = this.maxMp = mp;
        this.atk = atk; this.def = def;
        return this;
    }
    public NPCObject reward(int drop_exp, int drop_gold) {
        this.dropExp = drop_exp; this.dropGold = drop_gold;
        return this;
    }





    public void damaged(int damage) {
        this.hp = Math.max(0, this.hp - damage);
        if (hp <= 0) this.dead = true;
    }

    public void move(int dx, int dy) {
        this.x = dx; this.y = dy;
    }
    public void regen() {
        if(!dead) return;
        dead = false;
        this.hp = this.maxHp;
        this.mp = this.maxMp;
    }

    public long getEntityId() {
        return entityId;
    }

    public long getNpcId() {
        return npcId;
    }

    public boolean isDead() {
        return dead;
    }

    public int getDropExp() {
        return dropExp;
    }

    public int getDropGold() {
        return dropGold;
    }

    public int getDef() {
        return def;
    }

    public int getAtk() {
        return atk;
    }

    public int getVision() {
        return vision;
    }

    public int getMaxMp() {
        return maxMp;
    }

    public int getMp() {
        return mp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getHp() {
        return hp;
    }

    public int getLevel() {
        return level;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getTeam() {
        return team;
    }

    public Map getMap() {
        return map;
    }

    public long getMapId() {
        return mapId;
    }
}
