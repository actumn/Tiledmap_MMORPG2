package com.mygdx.game.object;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.map.Map;

/**
 * Created by Lee on 2016-05-19.
 */
public abstract class Entity implements DrawObject, Rectable {
    protected Map map;
    protected int entityId = 0;
    protected String name;
    public int x,y;
    protected int hp, maxHp, mp, maxMp;
    protected int direction;
    protected int s_direction;
    protected int s_attack;
    protected Animation walkAnimation;
    protected ShapeRenderer shapeRenderer;

    protected Rectangle bounds;

    protected int team;

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

    // 행동
    public abstract void action();

    public abstract void attack();

    // 스킬
    public abstract void skillAttack();



    // 갱신
    public abstract void update();


    public TextureRegion getTextureRegion() {
        final int walkAnimationsCount = 3;
        final int directionsCount = 4;
        return this.walkAnimation.getKeyFrame(
                this.direction * walkAnimationsCount + this.s_direction / directionsCount,
                false
        );
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
}
