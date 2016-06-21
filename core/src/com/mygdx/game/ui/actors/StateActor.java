package com.mygdx.game.ui.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.mygdx.game.object.Player;
import com.mygdx.game.ui.Font;
import com.mygdx.game.ui.MyShapeRenderer;

/**
 * Created by Lee on 2016-06-20.
 */
public class StateActor extends BaseActor {

    /* objects */
    private HealthBar healthBar;
    private MagicBar magicBar;
    private Player centerPlayer;
    private MyShapeRenderer shapeRenderer;

    /* font position properties */
    final int fontSize = 14;
    final float hpFontX, hpFontY;
    final float mpFontX, mpFontY;
    final float hpInfoX, hpInfoY;
    final float mpInfoX, mpInfoY;
    final float nameFontX, nameFontY;
    final float jobFontX, jobFontY;
    final float lvFontX, lvFontY;


    public StateActor(Player centerPlayer) {
        this.healthBar = new HealthBar();
        this.magicBar = new MagicBar();
        this.centerPlayer = centerPlayer;

        final float x = 50.0f, y = Gdx.graphics.getHeight() - 150.0f;
        final float width = 200.0f, height = 100.0f;
        this.setPosition(x, y);
        this.setSize(width, height);

        final float healthBarX = x + 40.0f, healthBarY = y + 35.0f;
        final float healthBarWidth = 100.0f, healthBarHeight = 10.0f;
        final float magicBarX = x + 40.0f, magicBarY = y + 15.0f;
        final float magicBarWidth = 100.0f, magicBarHeight = 10.0f;

        this.healthBar.setX(healthBarX); this.healthBar.setY(healthBarY);
        this.healthBar.setWidth(healthBarWidth); this.healthBar.setHeight(healthBarHeight);
        this.magicBar.setX(magicBarX); this.magicBar.setY(magicBarY);
        this.magicBar.setWidth(magicBarWidth); this.magicBar.setHeight(magicBarHeight);


        this.hpFontX = x + 10.0f; this.hpFontY = y + 45.0f;
        this.mpFontX = x + 10.0f; this.mpFontY = y + 25.0f;
        this.hpInfoX = x + width - 50.0f; this.hpInfoY = y + 45.0f;
        this.mpInfoX = x + width - 50.0f; this.mpInfoY = y + 25.0f;
        this.nameFontX = x + 10f; this.nameFontY = y + 80f;
        this.jobFontX = x + 100f; this.jobFontY = y + 80f;
        this.lvFontX = x + 170f; this.lvFontY = y + 80f;


        this.shapeRenderer = new MyShapeRenderer();
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.shapeRenderer.setColor(0.2f, 0.2f, 0.2f, 0.5f);
        this.shapeRenderer.roundedRect(getX(), getY(), getWidth(), getHeight(), 4);
        this.shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
        batch.begin();

        this.healthBar.setProgress(centerPlayer.getPercentHp()); this.magicBar.setProgress(centerPlayer.getPercentMp());
        this.healthBar.draw(batch, parentAlpha);
        this.magicBar.draw(batch, parentAlpha);


        BitmapFont infoFont = Font.getInstance().getFont(this.fontSize);
        infoFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        infoFont.draw(batch, "HP", this.hpFontX, this.hpFontY);
        infoFont.draw(batch, "MP", this.mpFontX, this.mpFontY);
        infoFont.draw(batch, centerPlayer.getHp()+"/"+centerPlayer.getMaxHp(), this.hpInfoX, this.hpInfoY);
        infoFont.draw(batch, centerPlayer.getMp()+"/"+centerPlayer.getMaxMp(), this.mpInfoX, this.mpInfoY);
        infoFont.draw(batch, centerPlayer.getName(), this.nameFontX, this.nameFontY);
        infoFont.draw(batch, centerPlayer.getJobName(), this.jobFontX, this.jobFontY);
        infoFont.draw(batch, String.valueOf(centerPlayer.getLevel()), this.lvFontX, this.lvFontY);
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
