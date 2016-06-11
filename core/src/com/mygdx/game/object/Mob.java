package com.mygdx.game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.map.Map;
import com.mygdx.game.ui.Font;

/**
 * Created by Lee on 2016-05-20.
 */
public class Mob extends NPC {
    private int nameWidth;
    private int dx, dy;
    public final int speedX = 5;
    public final int speedY = 5;

    public Mob() {
        this.entityState = EntityState.normal;
        this.x = 100;
        this.y = 200;
    }
    // builder pattern
    @Override
    public Mob setName(String name) {
        super.setName(name);
        this.nameColor.set(1.0f, 1.0f, 0.0f, 1.0f);
        return this;
    }
    public Mob setMap(Map map) {
        this.map = map;
        return this;
    }
    @Override
    public Mob stat(int hp, int mp, int atk, int def) {
        super.stat(hp, mp, atk, def);
        return this;
    }

    @Override
    public Mob loadAnimation(int stateValue, String key, int iIndex, int jIndex) {
        super.loadAnimation(stateValue, key, iIndex, jIndex);
        return this;
    }

    @Override
    public void update() {
        super.update();
        spriteUpdate();
    }


    private void spriteUpdate() {

        if(this.dx != this.x || this.dy != this.y || this.s_state != 0) {
            this.s_state += 1;
            if(this.s_state>=animationsCount*directionsCount) this.s_state = 0;

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
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }


    @Override
    public String toString() {
        return " ";
    }
}
