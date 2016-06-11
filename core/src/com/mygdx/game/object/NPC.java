package com.mygdx.game.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.map.Map;
import com.mygdx.game.scene.Assets;
import com.mygdx.game.ui.Font;

/**
 * Created by Lee on 2016-05-26.
 */
public class NPC extends Entity {

    /* drop data */
    protected int dropExp;
    protected int dropGold;

    /* stat properties */
    protected int vision;

    public NPC xy(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }
    public NPC setName(String name) {
        this.nameColor = new Color(0.0f, 1.0f, 0.0f, 1.0f);
        this.nameSize = 14;
        this.name = name;
        this.nameWidth = (int)(Font.getInstance().getFont(12).getSpaceWidth() * this.name.length() * 2);
        return this;
    }
    public NPC setMap(Map map) {
        this.map = map;
        return this;
    }
    public NPC vision(int vision) {
        this.vision = vision;
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
    public NPC stat(int hp, int mp, int atk, int def) {
        this.hp = this.maxHp = hp;
        this.mp = this.maxMp = mp;
        this.atk = atk;
        this.def = def;
        return this;
    }
    public NPC level(int level) {
        this.level = level;
        return this;
    }

    public NPC direction(int direction) {
        this.direction = direction;
        return this;
    }


    public NPC loadAnimation(int stateValue, String key, int iIndex, int jIndex) {
        final int horizontalCharactersCount = 4;
        final int verticalCharactersCount = 2;

        // load texture
        Texture animationSheet = Assets.getInstance().getSheet(key);

        TextureRegion[] frames = new TextureRegion[animationsCount * directionsCount];

        // split texture and load sprites
        TextureRegion[][] tmp = TextureRegion.split(animationSheet,
                animationSheet.getWidth() / (animationsCount * horizontalCharactersCount),
                animationSheet.getHeight() / (directionsCount * verticalCharactersCount)
        );
        // save sprites
        int index = 0;
        int iOffsetBegin = iIndex * directionsCount;
        int jOffsetBegin = jIndex * animationsCount;
        for (int i = iOffsetBegin; i < iOffsetBegin + directionsCount; i++) {
            for (int j = jOffsetBegin; j < jOffsetBegin + animationsCount; j++) {
                frames[index++] = tmp[i][j];
            }
        }

        this.animations.insert(stateValue, new Animation(1.0f, frames));

        this.shapeRenderer = new ShapeRenderer();

        this.bounds = new Rectangle(getDrawX(), getDrawY(),
                this.getTextureRegion().getRegionWidth(), this.getTextureRegion().getRegionHeight());

        return this;
    }

    public void Touch() {

    }
    public void AI_Update(){

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
        //AI_Update();
    }


    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(this.getTextureRegion(), this.getDrawX(), this.getDrawY());

        batch.end();

        // hp bar
        this.shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.shapeRenderer.setColor(0.8f, 0.0f, 0.0f, 1.0f);
        this.shapeRenderer.rect(
                getDrawX() + getTextureRegion().getRegionWidth() / 2 - 10, this.getDrawY() - 2, getPercentHp() / 5, 5
        );
        this.shapeRenderer.end();

        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        this.shapeRenderer.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        this.shapeRenderer.rect(
                this.getDrawX() + this.getTextureRegion().getRegionWidth() / 2 - 10,
                this.getDrawY() - 2,
                20, 5
        );
        this.shapeRenderer.end();

        batch.begin();

        Font.getInstance().getFont(this.nameSize).setColor(nameColor);
        Font.getInstance().getFont(this.nameSize).draw(batch, this.name,
                this.getDrawX() + this.getTextureRegion().getRegionWidth() / 2 - this.nameWidth / 2.0f,
                this.getDrawY() + this.getTextureRegion().getRegionHeight() + Font.getInstance().getFont(this.nameSize).getCapHeight());

    }

}
