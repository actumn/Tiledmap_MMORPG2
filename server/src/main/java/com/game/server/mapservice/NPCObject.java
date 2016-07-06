package com.game.server.mapservice;

import com.game.server.Server;

/**
 * Created by Lee on 2016-06-15.
 */
public class NPCObject {
    private long entityId;
    private int mapId;
    private Map map;

    private int npcId;
    private int x, y;
    private int hp, maxHp;

    private boolean dead;

    public NPCObject() {
        entityId = Server.uniqueId+=1;
        dead = false;
    }

    public NPCObject position(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }
    public void regen() {
        if(!dead) return;

        dead = false;
    }
    public void update(){

    }

    public long getEntityId() {
        return entityId;
    }

    public int getNpcId() {
        return npcId;
    }
}
