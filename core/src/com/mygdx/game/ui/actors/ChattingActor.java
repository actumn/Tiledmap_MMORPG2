package com.mygdx.game.ui.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.mygdx.game.ui.Font;

/**
 * Created by Lee on 2016-06-16.
 */
public class ChattingActor extends BaseActor {
    private Skin skin;
    private TextArea chatArea;
    private ScrollPane chatAreaPane;
    private TextField chatField;
    public ChattingActor(Skin skin) {
        this.skin = skin;

        this.chatArea = new TextArea("", skin);
        this.chatArea.setFillParent(true);
        this.chatAreaPane = new ScrollPane(chatArea, skin);
        this.chatField = new TextField("", skin);

        final float x = 0, y = 0;
        final float width = 380, height = 270;
        this.setX(x); this.setY(y);
        this.setWidth(width); this.setHeight(height);
        this.setColor(0.4f, 0.4f, 0.4f, 0.5f);


        final int textSize = 14;
        BitmapFont contentFont = Font.getInstance().getFont(textSize);
        this.chatArea.setStyle(getTextFieldStyle(skin, contentFont));
        this.chatField.setStyle(getTextFieldStyle(skin, contentFont));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        this.chatAreaPane.draw(batch, parentAlpha);
        this.chatField.draw(batch, parentAlpha);
    }



    private void chat() {

    }
}
