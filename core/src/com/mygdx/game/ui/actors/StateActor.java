package com.mygdx.game.ui.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

/**
 * Created by Lee on 2016-06-20.
 */
public class StateActor extends BaseActor {
    private HealthBar healthBar;
    private MagicBar magicBar;

    public StateActor() {
        this.healthBar = new HealthBar();
        this.magicBar = new MagicBar();
    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        this.healthBar.draw(batch, parentAlpha);
        this.magicBar.draw(batch, parentAlpha);
    }

    private class HealthBar extends BaseActor {
        private NinePatchDrawable healthBarBackground;
        private NinePatchDrawable healthBar;

        private HealthBar() {
            TextureAtlas skinAtlas = new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas"));
            NinePatch loadingBarBackgroundPatch = new NinePatch(skinAtlas.findRegion("default-round"), 5, 5, 4, 4);
            NinePatch loadingBarPatch = new NinePatch(skinAtlas.findRegion("default-round"), 5, 5, 4, 4);

            this.healthBar = new NinePatchDrawable(loadingBarPatch);
            this.healthBarBackground = new NinePatchDrawable(loadingBarBackgroundPatch);

            this.setX(100.0f);
            this.setY(Gdx.graphics.getHeight() - 100.0f);
            this.setWidth(100.0f);
            this.setHeight(10.0f);
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            float progress = 0.8f;

            batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            healthBarBackground.draw(batch, getX(), getY(), getWidth() * getScaleX(), getHeight() * getScaleY());
            batch.setColor(1.0f, 0.0f, 0.0f, 1.0f);
            healthBar.draw(batch, getX(), getY(), progress * getWidth() * getScaleX(), getHeight() * getScaleY());
        }
    }
    private class MagicBar extends BaseActor {
        private NinePatchDrawable magicBarBackground;
        private NinePatchDrawable magicBar;

        private MagicBar() {
            TextureAtlas skinAtlas = new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas"));
            NinePatch loadingBarBackgroundPatch = new NinePatch(skinAtlas.findRegion("default-round"), 5, 5, 4, 4);
            NinePatch loadingBarPatch = new NinePatch(skinAtlas.findRegion("default-round"), 5, 5, 4, 4);

            this.magicBar = new NinePatchDrawable(loadingBarPatch);
            this.magicBarBackground = new NinePatchDrawable(loadingBarBackgroundPatch);

            this.setX(100.0f);
            this.setY(Gdx.graphics.getHeight() - 120.0f);
            this.setWidth(100.0f);
            this.setHeight(10.0f);
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            float progress = 0.9f;

            batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            magicBarBackground.draw(batch, getX(), getY(), getWidth() * getScaleX(), getHeight() * getScaleY());
            batch.setColor(0.0f, 0.0f, 1.0f, 1.0f);
            magicBar.draw(batch, getX(), getY(), progress * getWidth() * getScaleX(), getHeight() * getScaleY());
        }
    }
}
