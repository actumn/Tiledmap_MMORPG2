package com.mygdx.game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.map.Map;
import com.mygdx.game.ui.Font;
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
    public EntityState entityState = EntityState.normal;

    /* texture properties */
    protected ChatBubble chatBubble;
    protected ShapeRenderer shapeRenderer;

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
        normal(0), attacking(1), casting(2), damaged(3);
        private int value;
        private EntityState(int value) { this.value = value; }
        public int getValue() {
            return value;
        }
    }

    public void chat(String message) {
        this.chatBubble.chat(this.name + ": " +message);
    }

    protected class ChatBubble {
        /* model */
        private Entity entity;

        /* view */
        private ShapeRenderer shapeRenderer;
        private int fontSize;


        /* properties */
        private GlyphLayout layout;
        private String message;
        private float r, g, b, a;
        private long expiryTime;
        private float chatWidth;
        private float chatHeight;
        private float horizontalPad = 10.0f;
        private float verticalPad = 10.0f;

        protected ChatBubble(Entity entity, ShapeRenderer shapeRenderer) {
            this.entity = entity;
            this.shapeRenderer = shapeRenderer;

            this.layout = new GlyphLayout();
            this.r = this.g = this.b = this.a = 1.0f;
            this.fontSize = 14;
        }
        private void chat(String message) {
            this.message = message;
            this.expiryTime = System.currentTimeMillis() + 1000;

            BitmapFont font = Font.getInstance().getFont(this.fontSize);
            this.layout.setText(font, message);
            this.chatWidth = layout.width;
            this.chatHeight =  layout.height;
        }

        protected void render(SpriteBatch batch) {
            if (System.currentTimeMillis() > this.expiryTime) return;

            batch.end();

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

            this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            this.shapeRenderer.setColor(0.5f, 0.5f, 0.5f, 0.4f);
            this.renderBackground(getDrawX(), getDrawY(), chatWidth + horizontalPad, chatHeight + verticalPad, 10);
            this.shapeRenderer.end();

            Gdx.gl.glDisable(GL20.GL_BLEND);

            batch.begin();


            BitmapFont chatFont = Font.getInstance().getFont(this.fontSize);
            chatFont.setColor(r, g, b, a);
            chatFont.draw(batch, this.message,
                    this.getDrawX() + this.horizontalPad / 2.0f,
                    this.getDrawY() + this.verticalPad / 2.0f + chatHeight);

        }

        private float getDrawX() {
            return this.entity.x - chatWidth / 2.0f - horizontalPad / 2.0f;
        }

        private float getDrawY() {
            return this.entity.y + this.entity.getTextureRegion().getRegionHeight() + 18;
        }

        private void renderBackground(float x, float y, float width, float height, float radius){
            // Central rectangle
            shapeRenderer.rect(x + radius, y + radius, width - 2*radius, height - 2*radius);

            // Four side rectangles, in clockwise order
            shapeRenderer.rect(x + radius, y, width - 2*radius, radius);
            shapeRenderer.rect(x + width - radius, y + radius, radius, height - 2*radius);
            shapeRenderer.rect(x + radius, y + height - radius, width - 2*radius, radius);
            shapeRenderer.rect(x, y + radius, radius, height - 2*radius);

            // Four arches, clockwise too
            shapeRenderer.arc(x + radius, y + radius, radius, 180f, 90f);
            shapeRenderer.arc(x + width - radius, y + radius, radius, 270f, 90f);
            shapeRenderer.arc(x + width - radius, y + height - radius, radius, 0f, 90f);
            shapeRenderer.arc(x + radius, y + height - radius, radius, 90f, 90f);
        }
    }
}
