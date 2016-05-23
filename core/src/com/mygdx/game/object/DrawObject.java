package com.mygdx.game.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Lee on 2016-05-19.
 */
public interface DrawObject {
    int getZ();
    void draw(SpriteBatch batch);

}
