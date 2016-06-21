package com.mygdx.game.ui.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.mygdx.game.object.Player;

/**
 * Created by Lee on 2016-06-20.
 */
public class StateActor extends BaseActor {
    private HealthBar healthBar;
    private MagicBar magicBar;
    private Player centerPlayer;

    public StateActor(Player centerPlayer) {
        this.healthBar = new HealthBar();
        this.magicBar = new MagicBar();
        this.centerPlayer = centerPlayer;

        this.healthBar.setX(100.0f); this.healthBar.setY(Gdx.graphics.getHeight() - 100.0f);
        this.healthBar.setWidth(100.0f); this.healthBar.setHeight(10.0f);
        this.magicBar.setX(100.0f); this.magicBar.setY(Gdx.graphics.getHeight() - 120.0f);
        this.magicBar.setWidth(100.0f); this.magicBar.setHeight(10.0f);


    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        this.healthBar.setProgress(centerPlayer.getPercentHp()); this.magicBar.setProgress(centerPlayer.getPercentMp());
        this.healthBar.draw(batch, parentAlpha);
        this.magicBar.draw(batch, parentAlpha);
    }

    private class HealthBar extends BaseActor {
        private float progress;
        private NinePatchDrawable healthBarBackground;
        private NinePatchDrawable healthBar;

        private HealthBar() {
            TextureAtlas skinAtlas = new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas"));
            NinePatch loadingBarBackgroundPatch = new NinePatch(skinAtlas.findRegion("default-round"), 5, 5, 4, 4);
            NinePatch loadingBarPatch = new NinePatch(skinAtlas.findRegion("default-round"), 5, 5, 4, 4);

            this.healthBar = new NinePatchDrawable(loadingBarPatch);
            this.healthBarBackground = new NinePatchDrawable(loadingBarBackgroundPatch);
        }

        public void setProgress(float progress) {
            this.progress = progress/100f;
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            healthBarBackground.draw(batch, getX(), getY(), getWidth() * getScaleX(), getHeight() * getScaleY());
            batch.setColor(1.0f, 0.0f, 0.0f, 1.0f);
            if (progress > 0)
                healthBar.draw(batch, getX(), getY(), progress * getWidth() * getScaleX(), getHeight() * getScaleY());
        }
    }
    private class MagicBar extends BaseActor {
        private float progress;
        private NinePatchDrawable magicBarBackground;
        private NinePatchDrawable magicBar;

        private MagicBar() {
            TextureAtlas skinAtlas = new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas"));
            NinePatch loadingBarBackgroundPatch = new NinePatch(skinAtlas.findRegion("default-round"), 5, 5, 4, 4);
            NinePatch loadingBarPatch = new NinePatch(skinAtlas.findRegion("default-round"), 5, 5, 4, 4);

            this.magicBar = new NinePatchDrawable(loadingBarPatch);
            this.magicBarBackground = new NinePatchDrawable(loadingBarBackgroundPatch);
        }

        public void setProgress(float progress) {
            this.progress = progress/100f;
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            magicBarBackground.draw(batch, getX(), getY(), getWidth() * getScaleX(), getHeight() * getScaleY());
            batch.setColor(0.0f, 0.0f, 1.0f, 1.0f);
            if (progress > 0)
                magicBar.draw(batch, getX(), getY(), progress * getWidth() * getScaleX(), getHeight() * getScaleY());
        }
    }
}
