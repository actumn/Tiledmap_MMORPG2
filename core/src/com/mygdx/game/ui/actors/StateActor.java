package com.mygdx.game.ui.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.mygdx.game.object.Player;
import com.mygdx.game.ui.Font;
import com.mygdx.game.ui.graphics.MyShapeRenderer;

/**
 * Created by Lee on 2016-06-20.
 */
public class StateActor extends BaseActor {

    /* objects */
    private StateBar healthBar;
    private StateBar magicBar;
    private StateBar expBar;
    private Player centerPlayer;
    private MyShapeRenderer shapeRenderer;

    /* font position properties */
    final int fontSize = 14;
    final float hpFontX, hpFontY;
    final float mpFontX, mpFontY;
    final float expFontX, expFontY;

    final float hpInfoX, hpInfoY;
    final float mpInfoX, mpInfoY;
    final float expInfoX, expInfoY;

    final float nameFontX, nameFontY;
    final float jobFontX, jobFontY;
    final float lvFontX, lvFontY;


    public StateActor(Player centerPlayer) {
        this.healthBar = new StateBar(Color.RED);
        this.magicBar = new StateBar(Color.BLUE);
        this.expBar = new StateBar(Color.GREEN);
        this.centerPlayer = centerPlayer;

        final float x = 50.0f, y = Gdx.graphics.getHeight() - 170.0f;
        final float width = 200.0f, height = 110.0f;
        this.setPosition(x, y);
        this.setSize(width, height);

        final float healthBarX = x + 40.0f, healthBarY = y + 55.0f;
        final float healthBarWidth = 100.0f, healthBarHeight = 10.0f;
        final float magicBarX = x + 40.0f, magicBarY = y + 35.0f;
        final float magicBarWidth = 100.0f, magicBarHeight = 10.0f;
        final float expBarX = x + 40.0f, expBarY = y + 15.0f;
        final float expBatWidth = 100.0f, expBarHeight = 10.0f;

        this.healthBar.setPosition(healthBarX, healthBarY);
        this.healthBar.setSize(healthBarWidth, healthBarHeight);
        this.magicBar.setPosition(magicBarX, magicBarY);
        this.magicBar.setSize(magicBarWidth, magicBarHeight);
        this.expBar.setPosition(expBarX, expBarY);
        this.expBar.setSize(expBatWidth, expBarHeight);


        this.hpFontX = x + 10.0f; this.hpFontY = y + 65.0f;
        this.mpFontX = x + 10.0f; this.mpFontY = y + 45.0f;
        this.expFontX = x + 10.0f; this. expFontY = y + 25.0f;
        this.hpInfoX = x + width - 50.0f; this.hpInfoY = y + 65.0f;
        this.mpInfoX = x + width - 50.0f; this.mpInfoY = y + 45.0f;
        this.expInfoX = x + width - 50.0f; this.expInfoY = y + 25.0f;
        this.nameFontX = x + 10f; this.nameFontY = y + 100f;
        this.jobFontX = x + 100f; this.jobFontY = y + 100f;
        this.lvFontX = x + 170f; this.lvFontY = y + 100f;


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

        this.healthBar.setProgress(centerPlayer.getPercentHp());
        this.magicBar.setProgress(centerPlayer.getPercentMp());
        this.expBar.setProgress(centerPlayer.getPercentExp());
        this.healthBar.draw(batch, parentAlpha);
        this.magicBar.draw(batch, parentAlpha);
        this.expBar.draw(batch, parentAlpha);


        BitmapFont infoFont = Font.getInstance().getFont(this.fontSize);
        infoFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        infoFont.draw(batch, "HP", this.hpFontX, this.hpFontY);
        infoFont.draw(batch, "MP", this.mpFontX, this.mpFontY);
        infoFont.draw(batch, "EXP", this.expFontX, this.expFontY);
        infoFont.draw(batch, centerPlayer.getHp()+"/"+centerPlayer.getMaxHp(), this.hpInfoX, this.hpInfoY);
        infoFont.draw(batch, centerPlayer.getMp()+"/"+centerPlayer.getMaxMp(), this.mpInfoX, this.mpInfoY);
        infoFont.draw(batch, centerPlayer.getPercentExp()+"%", this.expInfoX, this.expInfoY);
        infoFont.draw(batch, centerPlayer.getName(), this.nameFontX, this.nameFontY);
        infoFont.draw(batch, centerPlayer.getJobName(), this.jobFontX, this.jobFontY);
        infoFont.draw(batch, String.valueOf(centerPlayer.getLevel()), this.lvFontX, this.lvFontY);
    }

    private class StateBar extends BaseActor {
        private float progress;
        private NinePatchDrawable barBackground;
        private NinePatchDrawable bar;
        private Color color;

        private StateBar(Color color) {
            this.color = color;

            TextureAtlas skinAtlas = new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas"));
            NinePatch barBackgroundPatch = new NinePatch(skinAtlas.findRegion("default-round"), 5, 5, 4 ,4);
            NinePatch barPatch = new NinePatch(skinAtlas.findRegion("default-round"), 5, 5, 4, 4);

            this.bar = new NinePatchDrawable(barPatch);
            this.barBackground = new NinePatchDrawable(barBackgroundPatch);
        }

        public void setProgress(float progress) { this.progress = progress/100f; }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            batch.setColor(Color.WHITE);
            barBackground.draw(batch, getX(), getY(), getWidth() * getScaleX(), getHeight() * getScaleY());
            batch.setColor(color);
            if (progress > 0)
                bar.draw(batch, getX(), getY(), progress * getWidth() * getScaleX(), getHeight() * getScaleY());
        }
    }
}
