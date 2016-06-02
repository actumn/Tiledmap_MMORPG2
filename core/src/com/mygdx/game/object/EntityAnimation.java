package com.mygdx.game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Lee on 2016-05-30.
 */
public class EntityAnimation {
    private Entity entity;

    int walkAnimationsCount = 3;
    int attackAnimationsCount = 3;
    final int directionsCount = 4;

    protected Animation animation;

    public EntityAnimation(Entity entity) {
        this.entity = entity;
    }

    public void setWalkAnimationsCount(int walkAnimationsCount) {
        this.walkAnimationsCount = walkAnimationsCount;
    }

    public EntityAnimation loadAnimation() {
        final int iIndex = 0;
        final int jIndex = 0;
        final int horizontalCharactersCount = 1;
        final int verticalCharactersCount = 1;

        // load texture
        Texture walkSheet = CharacterTextureLoader.instance.walkSheet;
        Texture attackSheet = CharacterTextureLoader.instance.attackSheet;

        TextureRegion[] frames = new TextureRegion[walkAnimationsCount * directionsCount
                + attackAnimationsCount * directionsCount];

        // split texture and load sprites
        TextureRegion[][] tmp = TextureRegion.split(
                walkSheet, walkSheet.getWidth() / (walkAnimationsCount * horizontalCharactersCount),
                walkSheet.getHeight() / (directionsCount * verticalCharactersCount)
        );
        // save sprites
        int index = 0;
        int iOffsetBegin = iIndex * directionsCount;
        int jOffsetBegin = jIndex * walkAnimationsCount;
        for (int i = iOffsetBegin; i < iOffsetBegin + directionsCount; i++) {
            for (int j = jOffsetBegin; j < jOffsetBegin + walkAnimationsCount; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        tmp = TextureRegion.split(
                attackSheet, attackSheet.getWidth() / attackAnimationsCount,
                attackSheet.getHeight() / directionsCount
        );
        for (int i = iOffsetBegin; i < directionsCount; i++) {
            for (int j = jOffsetBegin; j < attackAnimationsCount; j++) {
                frames[index++] = tmp[i][j];
            }
        }

        this.animation = new Animation(1.0f, frames);



        return this;
    }

    public void update() {
        if( entity.entityState == Entity.EntityState.attacking ) {
            entity.s_attack += 1;
            if (entity.s_attack>= attackAnimationsCount * directionsCount) {
                entity.entityState = Entity.EntityState.normal;
                entity.s_attack = 0;
            }
        }

        if(entity.dx != entity.x || entity.dy != entity.y || entity.s_direction != 0) {
            entity.s_direction += 1;
            if(entity.s_direction >= walkAnimationsCount*directionsCount) entity.s_direction = 0;

        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(this.getTextureRegion(), entity.getDrawX(), entity.getDrawY());
    }

    public TextureRegion getTextureRegion() {
        int isAttacking = (entity.entityState == Entity.EntityState.attacking)? 1 : 0;

        return this.animation.getKeyFrame(
                entity.direction * walkAnimationsCount + entity.s_direction / directionsCount +
                        isAttacking * walkAnimationsCount * directionsCount + entity.s_attack / directionsCount,
                false
        );
    }


    private static class CharacterTextureLoader {
        private static CharacterTextureLoader instance = new CharacterTextureLoader();
        private Texture walkSheet;
        private Texture attackSheet;

        private CharacterTextureLoader() {
            this.walkSheet = new Texture(Gdx.files.internal("characters/character-move.png"));
            this.attackSheet = new Texture(Gdx.files.internal("characters/character-attack.png"));
        }
    }
}
