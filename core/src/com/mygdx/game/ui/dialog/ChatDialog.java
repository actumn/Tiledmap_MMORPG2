package com.mygdx.game.ui.dialog;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.ui.Font;
import com.mygdx.game.ui.actors.BaseActor;

/**
 * Created by Lee on 2016-06-16.
 */
public class ChatDialog extends BaseDialog {
    private Skin skin;
    private ScrollPane chatAreaPane;
    private TextField chatField;
    public ChatDialog(String title, Skin skin) {
        super(title, skin);

        final float x = 10, y = 10;
        final float width = 380, height = 230;
        final float horizontalPad = 10.0f;
        final float verticalPad = 10.0f;
        this.setX(x); this.setY(y);
        this.setWidth(width); this.setHeight(height);
        this.setColor(0.4f, 0.4f, 0.4f, 0.5f);


        final int textSize = 14;
        BitmapFont contentFont = Font.getInstance().getFont(textSize);


        Label chatArea = new Label("", skin);
        chatArea.setFillParent(true);
        chatArea.setStyle(getLabelStyle(skin, contentFont));

        this.chatAreaPane = new ScrollPane(chatArea, skin);
        this.chatAreaPane.setOverscroll(false, true);
        this.chatAreaPane.setSmoothScrolling(true);
        this.chatAreaPane.setFlickScroll(false);
        this.chatAreaPane.setPosition(horizontalPad / 2.0f, verticalPad / 2.0f + 35);
        this.chatAreaPane.setSize(width - horizontalPad / 2.0f, height - 65);


        this.chatField = new TextField("", skin);
        this.chatField.setStyle(getTextFieldStyle(skin, contentFont));
        this.chatField.setPosition(horizontalPad / 2.0f, verticalPad / 2.0f);
        this.chatField.setSize(width - horizontalPad / 2.0f, 30f);
        this.chatField.setStyle(getTextFieldStyle(skin, contentFont));

        this.addActor(this.chatAreaPane);
        this.addActor(chatField);
    }



    private void chat() {

    }
}
