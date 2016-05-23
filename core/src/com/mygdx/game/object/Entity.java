package com.mygdx.game.object;

import com.mygdx.game.map.Map;

/**
 * Created by Lee on 2016-05-19.
 */
public abstract class Entity implements DrawObject {
    protected Map map;
    protected int entityId = 0;
    protected String name;
    public int x;
    public int y;
    protected int hp, maxHp;

    public int getEntityId() {
        return entityId;
    }


    public float getPercentHp(){
        return hp * 100 / maxHp;
    }

    @Override
    public int getZ(){
        return 0;
    }

    public abstract void move(int sx, int sy, int dx, int dy);

    public abstract void update();
}
