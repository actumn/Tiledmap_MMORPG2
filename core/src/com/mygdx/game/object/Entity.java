package com.mygdx.game.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.map.Map;
import com.mygdx.game.ui.Font;

/**
 * Created by Lee on 2016-05-19.
 */
public abstract class Entity implements DrawObject, Rectable {
    protected Map map;
    protected int entityId = 0;
    protected String name;

    public int x,y;
    public int dx, dy;
    protected int direction;
    protected int s_direction;
    protected int s_attack;
    protected EntityAnimation entityAnimation;
    protected ShapeRenderer shapeRenderer;
    public EntityState entityState;

    /* name properties */
    protected Color nameColor;
    protected int nameSize;
    protected int nameWidth;
    protected Rectangle bounds;

    /* stat properties */
    protected int level;
    protected int hp, maxHp, mp, maxMp;

    protected int team = 0;

    public int getEntityId() {
        return entityId;
    }
    public float getPercentHp(){
        return hp * 100 / maxHp;
    }

    public int getDrawX() {
        return this.x - this.getTextureRegion().getRegionWidth() / 2;
    }
    public int getDrawY() {
        return this.y;
    }

    @Override
    public int getZ(){
        return 0;
    }

    // 이동
    public abstract void move(int sx, int sy, int dx, int dy);
    // 이동
    public abstract void show_move(int sx, int sy, int dx, int dy);

    // 행동
    public abstract void action();

    public abstract void attack();

    // 스킬
    public abstract void skillAttack();



    // 갱신
    public abstract void update();


    public TextureRegion getTextureRegion() {
        return entityAnimation.getTextureRegion();
    }

    public Rectangle getBounds() {
        return bounds;
    }

    protected enum DIRECTION {
        south(0), west(1), east(2), north(3);
        private int value;
        DIRECTION(int value) {
            this.value = value;
        }
        public int getValue() { return this.value; }
    }

    protected enum EntityState {
        normal, attacking
    }
}
