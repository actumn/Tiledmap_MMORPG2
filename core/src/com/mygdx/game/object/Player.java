package com.mygdx.game.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.map.Map;
import com.mygdx.game.scene.Assets;
import com.mygdx.game.ui.Font;

/**
 * Created by Lee on 2016-05-19.
 */
public class Player extends Entity {
    public final int speedX = 5;
    public final int speedY = 5;


    /* player job properties */
    private String jobName;
    private int lvhp, lvmp, lvatk, lvdef;


    private long skillCoolDown = 0;

    public Player() {
        this.s_state = 0;
        this.level = 1;
        this.x = 100;
        this.y = 100;
        this.entityState = EntityState.normal;

        this.team = 0;
    }
    public Player level(int level) {
        this.level = level;
        statUpdate();
        return this;
    }
    public Player xy(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }
    public Player setName(String name) {
        this.nameColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        this.nameSize = 14;
        this.name = name;
        this.nameWidth = (int)(Font.getInstance().getFont(12).getSpaceWidth() * this.name.length() * 2);
        return this;
    }
    public Player setMap(Map map) {
        this.map = map;
        return this;
    }
    public Player loadJob(String jobName, int lvhp, int lvmp, int lvatk, int lvdef) {
        this.jobName = jobName;
        this.lvhp = lvhp;
        this.lvmp = lvmp;
        this.lvatk = lvatk;
        this.lvdef = lvdef;
        return this;
    }
    public Player loadAnimation(int stateValue, String key) {
        return this.loadAnimation(stateValue, key, 0, 0);
    }
    public Player loadAnimation(int stateValue, String key, int iIndex, int jIndex) {
        final int horizontalCharactersCount = 1;
        final int verticalCharactersCount = 1;

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



    public void skillAttack(){
        if (System.currentTimeMillis() <= skillCoolDown) return;

        this.entityState = EntityState.casting;
        s_state = 1;

        Effect effect = new RectableEffect("effects/blue_crystal.png");

        double stand = ((double)direction - 1.5) * 2;
        /*  stand = north : 3
                    east : 1
                    west : -1
                    south : -3
        */
        int skillX = (int)stand % 3;
        int skillY = (int)stand / 3;
        /*
            x,y =   north : 0,1
                    east : 1,0
                    west : -1,0
                    south : 0, -1
         */
        final int distance = 70;
        skillX *= distance; skillY *= distance;

        effect.setXY(this.x + skillX, this.y + skillY);

        map.add(effect);

        skillCoolDown = System.currentTimeMillis() + 2000;
    }

    @Override
    public void action() {

    }

    @Override
    public void attack() {
        this.entityState = EntityState.attacking;

        s_state = 1;
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

    public void update() {
        if(dx != x || dy != y || s_state != 0) {
            s_state += 1;
            if(this.s_state >= animationsCount*directionsCount) {
                this.s_state = 0;
                this.entityState = EntityState.normal;
            }
        }

        this.dx = this.x;
        this.dy = this.y;
    }

    private void statUpdate() {
        this.hp = this.maxHp = this.lvhp * this.level;
        this.mp = this.maxMp = this.lvmp * this.level;
        this.atk = this.lvatk * this.level;
        this.def = this.lvdef * this.level;
    }

    public int getDrawX() {
        return this.x - this.getTextureRegion().getRegionWidth() / 2;
    }
    public int getDrawY() {
        return this.y;
    }

    @Override
    public String toString() {
        return name;
    }

}
