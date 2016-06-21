package com.mygdx.game.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.map.Map;
import network.Network;
import protocol.Packet.PacketFactory;

/**
 * Created by Lee on 2016-05-19.
 */
public abstract class Entity implements DrawObject, Rectable {
    protected Map map;
    protected long entityId = 0;
    protected String name;

    public int x,y;
    public int dx, dy;
    protected int direction;
    protected int s_state;
    protected ShapeRenderer shapeRenderer;
    public EntityState entityState = EntityState.normal;

    /* animations properties */
    protected Array<Animation> animations = new Array<>();
    protected final int animationsCount = 3;
    protected final int directionsCount = 4;

    /* name properties */
    protected Color nameColor;
    protected int nameSize;
    protected int nameWidth;
    protected Rectangle bounds;

    /* stat properties */
    protected int level;
    protected int hp, maxHp, mp, maxMp;
    protected int atk, def;

    protected int team = 0;

    public Entity entityId(long entityId) {
        this.entityId = entityId;
        return this;
    }


    public long getEntityId() {
        return entityId;
    }
    public float getPercentHp(){
        return maxHp == 0? 0 : hp * 100 / maxHp;
    }
    public float getPercentMp() {
        return maxMp == 0? 0 : mp * 100 / maxMp;
    }

    public int getDrawX() {
        return this.x - this.getTextureRegion().getRegionWidth() / 2;
    }
    public int getDrawY() {
        return this.y;
    }

    @Override
    public int getZ(){
        return this.y;
    }

    // 이동 시도
    public void move(int sx, int sy, int dx, int dy) {
        if (entityState != EntityState.normal) return;
        else if (map.checkCollision(dx, dy)) return;
        else if (map.isCollide(this, new Rectangle(dx - this.getTextureRegion().getRegionWidth() / 2, dy,
                this.getBoundsWidth(), this.getBoundsHeight())))  return;

        PacketFactory packetFactory = Network.getInstance().getPacketFactory();
        Network.getInstance().send(packetFactory.move(this.entityId, this.map.getMapId(),dx, dy));

        show_move(sx, sy, dx, dy);
    }
    // 이동
    public void show_move(int sx, int sy, int dx, int dy) {
        this.x = dx;
        this.y = dy;

        int ax = dx - sx;
        int ay = dy - sy;

        if(ay<0) this.direction = Direction.south.getValue();
        if(ax<0) this.direction = Direction.west.getValue();
        if(ax>0) this.direction = Direction.east.getValue();
        if(ay>0) this.direction = Direction.north.getValue();

        this.bounds.setPosition(this.getDrawX(), this.getDrawY());
    }

    // 행동
    public abstract void action();

    public abstract void attack();

    // 스킬
    public abstract void skillAttack();



    // 갱신
    public abstract void update();


    public TextureRegion getTextureRegion() {
        return animations.get(this.entityState.getValue()).
                getKeyFrame(
                this.direction * animationsCount + this.s_state / directionsCount
        );
    }

    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return this.name;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public float getBoundsWidth() {
        return this.bounds.getWidth();
    }
    public float getBoundsHeight() {
        return this.bounds.getHeight();
    }


    protected enum Direction {
        south(0), west(1), east(2), north(3);
        private int value;
        private Direction(int value) {
            this.value = value;
        }
        public int getValue() { return this.value; }
    }

    public enum EntityState {
        normal(0), attacking(1), casting(2);
        private int value;
        private EntityState(int value) { this.value = value; }
        public int getValue() {
            return value;
        }
    }
}
