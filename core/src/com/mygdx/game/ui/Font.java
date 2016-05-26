package com.mygdx.game.ui;

/**
 * Created by Lee on 2016-05-26.
 */

import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Font {
    private static BitmapFont fonts = new BitmapFont();

    public static BitmapFont getFont() {
        return fonts;
    }
}