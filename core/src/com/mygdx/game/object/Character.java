package com.mygdx.game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Lee on 2016-05-19.
 */
public class Character extends Entity implements Rectable{
    private int dx, dy;
    private int s_direction;
    public int direction;
    public int speedX = 5;
    public int speedY = 5;

    private Animation walkAnimation;
    private ShapeRenderer shapeRenderer;

    public Character() {
        this.x = 100;
        this.y = 100;
        hp = maxHp = 100;
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
        final int iOffsetBegin = iIndex * directionsCount;
        final int jOffsetBegin = jIndex * walkAnimationsCount;
        for (int i = iOffsetBegin; i < iOffsetBegin + directionsCount; i++) {
            for (int j = jOffsetBegin; j < jOffsetBegin + walkAnimationsCount; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        this.walkAnimation = new Animation(1.0f, walkFrames);
        this.shapeRenderer = new ShapeRenderer();


        return this;
    }


    @Override
    public int getZ() {
        return this.y + 64;
    }

    @Override
    public void move(int sx, int sy, int dx, int dy) {
        this.x = dx;
        this.y = dy;

        int ax = dx - sx;
        int ay = dy - sy;

        if(ay<0) this.direction = 0;
        if(ax<0) this.direction = 1;
        if(ax>0) this.direction = 2;
        if(ay>0) this.direction = 3;
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

    }

    public void update() {
        final int walkAnimationsCount = 3;
        final int directionsCount = 4;

        if(this.dx != this.x || this.dy != this.y || this.s_direction != 0) {
            this.s_direction += 1;
            if(this.s_direction>=3*4) this.s_direction = 0;

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

    public TextureRegion getTextureRegion() {
        final int walkAnimationsCount = 3;
        final int directionsCount = 4;
        return this.walkAnimation.getKeyFrame(
                this.direction * walkAnimationsCount + this.s_direction / directionsCount,
                true
        );
    }

    @Override
    public Rectangle getRectangle() {
        return null;
    }

    private static class CharacterTextureLoader {
        private static CharacterTextureLoader instance = new CharacterTextureLoader();
        private Texture walkSheet;

        private CharacterTextureLoader() {
            this.walkSheet = new Texture(Gdx.files.internal("characters/characters.png"));
        }
    }
}
