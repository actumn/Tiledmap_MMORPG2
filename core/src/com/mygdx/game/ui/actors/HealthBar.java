package com.mygdx.game.ui.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

/**
 * Created by Lee on 2016-05-23.
 */
public class HealthBar extends BaseActor {
    private NinePatchDrawable loadingBarBackground;
    private NinePatchDrawable loadingBar;

    public HealthBar() {
        TextureAtlas skinAtlas = new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas"));
        NinePatch loadingBarBackgroundPatch = new NinePatch(skinAtlas.findRegion("default-round"), 5, 5, 4, 4);
        NinePatch loadingBarPatch = new NinePatch(skinAtlas.findRegion("default-round-down"), 5, 5, 4, 4);

        this.loadingBar = new NinePatchDrawable(loadingBarPatch);
        this.loadingBarBackground = new NinePatchDrawable(loadingBarBackgroundPatch);

        this.setX(100.0f);
        this.setY(Gdx.graphics.getHeight() - 100.0f);
        this.setWidth(100.0f);
        this.setHeight(10.0f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float progress = 1.0f;

        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        loadingBarBackground.draw(batch, getX(), getY(), getWidth() * getScaleX(), getHeight() * getScaleY());
        loadingBar.draw(batch, getX(), getY(), progress * getWidth() * getScaleX(), getHeight() * getScaleY());
    }
}
