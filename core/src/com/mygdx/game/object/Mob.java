package com.mygdx.game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.map.Map;

/**
 * Created by Lee on 2016-05-20.
 */
public class Mob extends NPC {
    private int nameWidth;
    private int dx, dy;
    private Map map;
    public final int speedX = 5;
    public final int speedY = 5;

    public Mob() {
        this.x = 100;
        this.y = 200;
    }

    public Mob setMap(Map map) {
        this.map = map;
        return this;
    }
    // builder pattern
    public Mob loadAnimation() {
        final int walkAnimationsCount = 3;
        final int directionsCount = 4;

        // load texture
        Texture walkSheet = MobTextureLoader.instance.walkSheet;

        // split texture and load sprites
        TextureRegion[][] tmp = TextureRegion.split(
                walkSheet, walkSheet.getWidth() / walkAnimationsCount,
                walkSheet.getHeight() / directionsCount
        );
        TextureRegion[] walkFrames = new TextureRegion[walkAnimationsCount * directionsCount];

        // save sprites
        int index = 0;
        int iOffsetBegin = 0;
        int jOffsetBegin = 0;
        for (int i = iOffsetBegin; i < iOffsetBegin + directionsCount; i++) {
            for (int j = jOffsetBegin; j < jOffsetBegin + walkAnimationsCount; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        //this.walkAnimation = new Animation(1.0f, walkFrames);
        // walk animation end

        this.bounds = new Rectangle(getDrawX(), getDrawY(),
                this.getTextureRegion().getRegionWidth(), this.getTextureRegion().getRegionHeight());

        return this;
    }

    @Override
    public void update() {
        super.update();
        spriteUpdate();
        AI_Update();
    }

    private void spriteUpdate() {
        final int walkAnimationsCount = 3;
        final int directionsCount = 4;

        if(this.dx != this.x || this.dy != this.y || this.s_direction != 0) {
            this.s_direction += 1;
            if(this.s_direction>=walkAnimationsCount*directionsCount) this.s_direction = 0;

            this.dx = this.x;
            this.dy = this.y;
        }
    }

    private long moveCoolDown = 0;
    @Override
    public void AI_Update() {
        if (System.currentTimeMillis() < moveCoolDown) return;
        final int speed = 32;
        int dir = (int)(Math.random()*4);   // will gen 0 1 2 3
        double stand = ((double)dir - 1.5) * 2;
        /*  stand = north : 3
                    east : 1
                    west : -1
                    south : -3
        */
        int dirX = (int)stand % 3;
        int dirY = (int)stand / 3;
        /*
            x,y =   north : 0,1
                    east : 1,0
                    west : -1,0
                    south : 0, -1
         */
        dirX *= 32; dirY *= 32;
        move(this.x, this.y, this.x + dirX, this.y + dirY);
        moveCoolDown = System.currentTimeMillis() + 1000;
    }

    @Override
    public int getZ() {
        return this.y ;
    }

    @Override
    public void move(int sx, int sy, int dx, int dy) {
        if (map.checkCollision(dx, dy)) return;
        show_move(sx, sy, dx, dy);
    }

    @Override
    public void show_move(int sx, int sy, int dx, int dy) {
        this.x = dx;
        this.y = dy;

        int ax = dx - sx;
        int ay = dy - sy;

        if(ay<0) this.direction = DIRECTION.south.getValue();
        if(ax<0) this.direction = DIRECTION.west.getValue();
        if(ax>0) this.direction = DIRECTION.east.getValue();
        if(ay>0) this.direction = DIRECTION.north.getValue();

        this.bounds.setPosition(this.getDrawX(), this.getDrawY());
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(this.getTextureRegion(), this.getDrawX(), this.getDrawY());
    }


    private static class MobTextureLoader {
        private static MobTextureLoader instance = new MobTextureLoader();
        private Texture walkSheet;

        private MobTextureLoader() {
            this.walkSheet = new Texture(Gdx.files.internal("npcs/monster.png"));
        }
    }

    @Override
    public String toString() {
        return " ";
    }
}
