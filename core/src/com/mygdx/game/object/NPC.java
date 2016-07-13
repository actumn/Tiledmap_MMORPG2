package com.mygdx.game.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.map.Map;
import com.mygdx.game.scene.Assets;
import com.mygdx.game.ui.Font;
import com.mygdx.game.ui.graphics.EntitySheet;
import com.mygdx.game.ui.graphics.MyShapeRenderer;

/**
 * Created by Lee on 2016-05-26.
 */
public class NPC extends Entity {

    /* drop data */
    protected int dropExp;
    protected int dropGold;

    /* stat properties */
    protected int vision;

    public NPC position(int x, int y) {
        this.x = x;
        this.y = y;
        this.bounds.setPosition(this.getDrawX(),this.getDrawY());
        return this;
    }
    @Override
    public NPC entityId(long entityId) {
        super.entityId(entityId);
        return this;
    }
    public NPC setName(String name) {
        this.nameSize = 14;
        this.name = name;

        BitmapFont font = Font.getInstance().getFont(12);
        GlyphLayout layout = new GlyphLayout(font, name);
        this.nameWidth = layout.width;
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
        if (team == 0) this.nameColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        else if (team == -1) this.nameColor = new Color(0.0f, 1.0f, 0.0f, 1.0f);
        else if (team == 1) this.nameColor = new Color(1.0f, 1.0f, 0.0f, 1.0f);
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
        // load texture
        EntitySheet animationSheet = Assets.getInstance().getSheet(key);
        final int horizontalCharactersCount = animationSheet.getHorizontalCharactersCount();
        final int verticalCharactersCount = animationSheet.getVerticalCharactersCount();

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

        this.shapeRenderer = new MyShapeRenderer();

        this.bounds = new Rectangle(this.getDrawX(), this.getDrawY(),
                this.getTextureRegion().getRegionWidth(), this.getTextureRegion().getRegionHeight());

        return this;
    }

    public void Touch() {

    }

    protected void spriteUpdate() {
        if(this.dx != this.x || this.dy != this.y || this.s_state != 0) {
            this.s_state += 1;
            if(this.s_state>=animationsCount*directionsCount) this.s_state = 0;

            this.dx = this.x;
            this.dy = this.y;
        }
    }

    private long moveCoolDown = 0;
    public void AI_Update(){
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
        spriteUpdate();
    }


    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(this.getTextureRegion(), this.getDrawX(), this.getDrawY());


        Font.getInstance().getFont(this.nameSize).setColor(nameColor);
        Font.getInstance().getFont(this.nameSize).draw(batch, this.name,
                this.getDrawX() + this.getTextureRegion().getRegionWidth() / 2 - this.nameWidth / 2.0f,
                this.getDrawY() + this.getTextureRegion().getRegionHeight() + Font.getInstance().getFont(this.nameSize).getCapHeight());

        if (team == 0) return;

        batch.end();

        // hp bar
        this.shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.shapeRenderer.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        this.shapeRenderer.rect(
                this.getDrawX() + this.getTextureRegion().getRegionWidth() / 2 - 10,
                this.getDrawY() - 2,
                20, 5
        );

        this.shapeRenderer.setColor(0.8f, 0.0f, 0.0f, 1.0f);
        this.shapeRenderer.rect(
                getDrawX() + getTextureRegion().getRegionWidth() / 2 - 10, this.getDrawY() - 2, getPercentHp() / 5, 5
        );
        this.shapeRenderer.end();

        batch.begin();


    }

}
