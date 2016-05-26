package com.mygdx.game.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Lee on 2016-05-19.
 */
public interface DrawObject {
    int getZ();
    int getDrawX();
    int getDrawY();
    TextureRegion getTextureRegion();
    void draw(SpriteBatch batch);
}
