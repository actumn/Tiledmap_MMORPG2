package com.mygdx.game.ui.actors.tables;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.*;

/**
 * Created by Lee on 2016-06-22.
 */
public abstract class BaseTable extends Table {

    public BaseTable(Skin skin) {
        super(skin);
    }

    public TextButton.TextButtonStyle getTextButtonStyle(Skin skin, BitmapFont font) {
        return new TextButton.TextButtonStyle(skin.getDrawable("default-round"), skin.getDrawable("default-round-down"), skin.getDrawable("default-round"), font);
    }

    public TextField.TextFieldStyle getTextFieldStyle(Skin skin, BitmapFont font) {
        return new TextField.TextFieldStyle(font, skin.getColor("white"), skin.getDrawable("cursor"), skin.getDrawable("selection"), skin.getDrawable("textfield"));
    }

    public Label.LabelStyle getLabelStyle(Skin skin, BitmapFont font) {
        return new Label.LabelStyle(font, skin.getColor("white"));
    }

    public List.ListStyle getListStyle(Skin skin, BitmapFont font) {
        return new List.ListStyle(font, skin.getColor("white"), skin.getColor("white"), skin.getDrawable("selection"));
    }

    public ScrollPane.ScrollPaneStyle getScrollPaneStyle(Skin skin, BitmapFont font) {
        return new ScrollPane.ScrollPaneStyle(skin.getDrawable("default-rect"), skin.getDrawable("default-scroll"), skin.getDrawable("default-round-large"), skin.getDrawable("default-scroll"), skin.getDrawable("default-round-large"));
    }


    public SelectBox.SelectBoxStyle getSelectBoxStyle(Skin skin, BitmapFont font) {
        return new SelectBox.SelectBoxStyle(font, skin.getColor("white"), skin.getDrawable("default-select"), getScrollPaneStyle(skin, font), new List.ListStyle(font, skin.getColor("black"), skin.getColor("white"), skin.getDrawable("default-select-selection")));
    }

}
