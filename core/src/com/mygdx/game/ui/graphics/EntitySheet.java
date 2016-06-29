package com.mygdx.game.ui.graphics;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Lee on 2016-06-29.
 */
public class EntitySheet extends Texture {
    private int horizontalCharactersCount = 1;
    private int verticalCharactersCount = 1;

    public EntitySheet(FileHandle file) {
        super(file);
    }

    public EntitySheet charactersCount(int horizontalCharactersCount, int verticalCharactersCount) {
        this.horizontalCharactersCount = horizontalCharactersCount;
        this.verticalCharactersCount = verticalCharactersCount;

        return this;
    }

    public int getHorizontalCharactersCount() {
        return horizontalCharactersCount;
    }

    public int getVerticalCharactersCount() {
        return verticalCharactersCount;
    }
}
