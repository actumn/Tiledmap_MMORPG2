package com.mygdx.game.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.ui.Font;

/**
 * Created by Lee on 2016-05-26.
 */
public class NPC extends Entity {

    /* drop data */
    protected int dropExp;
    protected int dropGold;

    /* stat properties */
    protected int atk;
    protected int def;
    protected int vision;

    public NPC xy(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }
    public NPC setName(String name) {
        this.nameColor = new Color(1.0f, 1.0f, 0.0f, 1.0f);
        this.name = name;
        this.nameWidth = (int)(Font.getInstance().getFont(12).getSpaceWidth() * this.name.length() * 2);
        return this;
    }
    public NPC team(int team) {
        this.team = team;
        return this;
    }
    public NPC dropExpGold(int dropExp, int dropGold) {
        this.dropExp = dropExp;
        this.dropGold = dropGold;
        return this;
    }
    public NPC atkDef(int atk, int def) {
        this.atk = atk;
        this.def = def;
        return this;
    }



    public NPC direction(int direction) {
        this.direction = direction;
        return this;
    }

    public void Touch() {

    }
    public void AI_Update(){

    }
    @Override
    public void move(int sx, int sy, int dx, int dy) {

    }

    @Override
    public void show_move(int sx, int sy, int dx, int dy) {

    }

    @Override
    public void action() {

    }

    @Override
    public void attack() {

    }

    @Override
    public void skillAttack() {

    }

    @Override
    public void update() {
        AI_Update();
    }


    @Override
    public void draw(SpriteBatch batch) {

    }

}
