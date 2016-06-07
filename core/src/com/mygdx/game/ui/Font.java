package com.mygdx.game.ui;

/**
 * Created by Lee on 2016-05-26.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;

public class Font {
    private static Font instance = new Font();
    public static Font getInstance() { return instance; }

    private int uiFontSize = 13;

    private HashMap<Integer, BitmapFont> fonts = new HashMap<Integer, BitmapFont>();
    public void reloadFont() { fonts.clear(); }

    public BitmapFont getFont(int size) {
        if (!fonts.containsKey(size)) {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/NanumGothic.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = size;
            parameter.characters = Gdx.files.internal("fonts/char.txt").readString();
            parameter.minFilter = Texture.TextureFilter.Nearest;
            parameter.magFilter = Texture.TextureFilter.MipMapLinearNearest;

            fonts.put(size, generator.generateFont(parameter));
            generator.dispose();
        }
        return fonts.get(size);
    }

    public int getUiFontSize() {
        return uiFontSize;
    }
}