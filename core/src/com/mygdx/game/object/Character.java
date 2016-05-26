package com.mygdx.game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.map.Map;
import com.mygdx.game.ui.Font;

/**
 * Created by Lee on 2016-05-19.
 */
public class Character extends Entity {
    private int nameWidth;
    private int dx, dy;
    private Map map;
    private CharacterState characterState;
    public final int speedX = 5;
    public final int speedY = 5;
    private Animation attackAnimation;


    private long skillCoolDown = 0;

    public Character() {
        this.x = 100;
        this.y = 100;
        hp = maxHp = 100;
        characterState = CharacterState.normal;

        team = 0;
    }
    public Character setName(String name) {
        this.name = name;
        this.nameWidth = (int)(Font.getFont().getSpaceWidth() * this.name.length() * 2);
        return this;
    }
    public Character setMap(Map map) {
        this.map = map;
        return this;
    }
    public Character loadAnimation() {
        return loadAnimation(0, 0);
    }
    public Character loadAnimation(int index) {
        final int horizontalCharactersCount = 4;
        int iIndex = index / horizontalCharactersCount;
        int jIndex = index % horizontalCharactersCount;

        return loadAnimation(iIndex, jIndex);
    }
    public Character loadAnimation(int iIndex, int jIndex) {
        final int walkAnimationsCount = 3;
        final int directionsCount = 4;
        final int horizontalCharactersCount = 4;
        final int verticalCharactersCount = 2;

        // Out of index
        if (iIndex > verticalCharactersCount || jIndex > horizontalCharactersCount) return this;

        // load texture
        Texture walkSheet = CharacterTextureLoader.instance.walkSheet;

        // split texture and load sprites
        TextureRegion[][] tmp = TextureRegion.split(
                walkSheet, walkSheet.getWidth() / (walkAnimationsCount * horizontalCharactersCount),
                walkSheet.getHeight() / (directionsCount * verticalCharactersCount)
        );
        TextureRegion[] walkFrames = new TextureRegion[walkAnimationsCount * directionsCount];

        // save sprites
        int index = 0;
        int iOffsetBegin = iIndex * directionsCount;
        int jOffsetBegin = jIndex * walkAnimationsCount;
        for (int i = iOffsetBegin; i < iOffsetBegin + directionsCount; i++) {
            for (int j = jOffsetBegin; j < jOffsetBegin + walkAnimationsCount; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        this.walkAnimation = new Animation(1.0f, walkFrames);
        // walk animation end

        final int attackAnimationCount = 4;
        // attack animation
        Texture attackSheet = CharacterTextureLoader.instance.attackSheet;

        tmp = TextureRegion.split(
                attackSheet, attackSheet.getWidth() / attackAnimationCount,
                attackSheet.getHeight() / directionsCount
        );
        TextureRegion[] attackFrames = new TextureRegion[attackAnimationCount * directionsCount];

        index = 0;
        iOffsetBegin = 0;
        jOffsetBegin = 0;
        for (int i = iOffsetBegin; i < directionsCount; i++) {
            for (int j = jOffsetBegin; j < attackAnimationCount; j++) {
                attackFrames[index++] = tmp[i][j];
            }
        }
        this.attackAnimation = new Animation(1.0f, attackFrames);


        this.shapeRenderer = new ShapeRenderer();

        this.bounds = new Rectangle(getDrawX(), getDrawY(),
                this.getTextureRegion().getRegionWidth(), this.getTextureRegion().getRegionHeight());

        return this;
    }


    @Override
    public int getZ() {
        return this.y - 64;
    }

    public void skillAttack(){
        if (System.currentTimeMillis() <= skillCoolDown) return;

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
    public void move(int sx, int sy, int dx, int dy) {
        if (characterState == CharacterState.attacking || map.checkCollision(dx, dy)) return;
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
    public void action() {

    }

    @Override
    public void attack() {
        characterState = CharacterState.attacking;

        s_attack = 0;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(this.getTextureRegion(), this.getDrawX(), this.getDrawY());
        drawMotion(batch);
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

        Font.getFont().setColor(1.0f, 1.0f, 1.0f, 1.0f);
        Font.getFont().draw(batch, this.name,
                this.getDrawX() + this.getTextureRegion().getRegionWidth() / 2 - this.nameWidth / 2.0f,
                this.getDrawY() + this.getTextureRegion().getRegionHeight() + Font.getFont().getCapHeight());
    }
    private void drawMotion(SpriteBatch batch) {
        if (characterState != CharacterState.attacking) return;
        double stand = ((double)direction - 1.5) * 2;
        /*  stand = north : 3
                    east : 1
                    west : -1
                    south : -3
        */
        int attackX = (int)stand % 3;
        int attackY = (int)stand / 3;
        /*
            x,y =   north : 0,1
                    east : 1,0
                    west : -1,0
                    south : 0, -1
         */
        attackX *= 30; attackY *= 30;
        int characterHeight = this.getTextureRegion().getRegionHeight();
        int motionWidth = this.getAttackTextureRegion().getRegionWidth();
        int motionHeight = this.getAttackTextureRegion().getRegionHeight();
        batch.draw(this.getAttackTextureRegion(), this.x - motionWidth/2 + attackX, this.y - motionHeight/2 + characterHeight / 2 + attackY);
    }

    public void update() {
        final int walkAnimationsCount = 3;
        final int attackAnimationCount = 4;
        final int directionsCount = 4;

        if( characterState == CharacterState.attacking ) {
            this.s_attack += 1;
            if (this.s_attack>= attackAnimationCount * directionsCount) {
                this.characterState = CharacterState.normal;
                this.s_attack = 0;
            }
        }

        if(this.dx != this.x || this.dy != this.y || this.s_direction != 0) {
            this.s_direction += 1;
            if(this.s_direction>=walkAnimationsCount*directionsCount) this.s_direction = 0;

            this.dx = this.x;
            this.dy = this.y;
        }
    }

    public int getDrawX() {
        return this.x - this.getTextureRegion().getRegionWidth() / 2;
    }
    public int getDrawY() {
        return this.y;
    }

    public TextureRegion getAttackTextureRegion() {
        final int attackAnimationsCount = 4;
        final int directionsCount = 4;
        return this.attackAnimation.getKeyFrame(
                this.direction * attackAnimationsCount + this.s_attack / directionsCount,
                false
        );
    }


    private static class CharacterTextureLoader {
        private static CharacterTextureLoader instance = new CharacterTextureLoader();
        private Texture walkSheet;
        private Texture attackSheet;

        private CharacterTextureLoader() {
            this.walkSheet = new Texture(Gdx.files.internal("characters/characters.png"));
            this.attackSheet = new Texture(Gdx.files.internal("characters/attack.png"));
        }
    }

    @Override
    public String toString() {
        return name;
    }

    private enum CharacterState {
        normal, attacking
    }
}
