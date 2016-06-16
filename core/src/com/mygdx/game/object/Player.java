package com.mygdx.game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.map.Map;
import com.mygdx.game.scene.Assets;
import com.mygdx.game.ui.Font;

/**
 * Created by Lee on 2016-05-19.
 */
public class Player extends Entity {
    public final int speedX = 5;
    public final int speedY = 5;

    private ChatBubble chatBubble;

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

    @Override
    public Player entityId(long entityId) {
        super.entityId(entityId);
        return this;
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
        this.chatBubble = new ChatBubble(this, shapeRenderer);

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

        this.chatBubble.render(batch);
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

    public void chat() {
        this.chatBubble.chat(this.name + ":\n default\n message");
    }


    @Override
    public String toString() {
        return name;
    }

    private class ChatBubble {
        /* model */
        private Player player;

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

        private ChatBubble(Player player, ShapeRenderer shapeRenderer) {
            this.player = player;
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

        private void render(SpriteBatch batch) {
            if (System.currentTimeMillis() > this.expiryTime) return;

            batch.end();

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

            this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            this.shapeRenderer.setColor(0.5f, 0.5f, 0.5f, 0.4f);
            this.render(getDrawX(), getDrawY(), chatWidth + horizontalPad, chatHeight + verticalPad, 4);
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
            return this.player.x - chatWidth / 2.0f - horizontalPad / 2.0f;
        }

        private float getDrawY() {
            return this.player.y + this.player.getTextureRegion().getRegionHeight() + 18;
        }

        private void render(float x, float y, float width, float height, float radius){
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
