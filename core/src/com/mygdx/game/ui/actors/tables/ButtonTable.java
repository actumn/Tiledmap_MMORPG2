package com.mygdx.game.ui.actors.tables;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.ui.Font;

/**
 * Created by Lee on 2016-06-23.
 */
public class ButtonTable extends BaseTable {
    public ButtonTable(Skin skin) {
        super(skin);

        BitmapFont font = Font.getInstance().getFont(14);
        TextButton quitButton = new TextButton("", skin);
        quitButton.setText("quit");
        quitButton.setStyle(getTextButtonStyle(skin, font));
    }
}
