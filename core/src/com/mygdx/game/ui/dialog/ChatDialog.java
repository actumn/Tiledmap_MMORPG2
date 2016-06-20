package com.mygdx.game.ui.dialog;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.StringBuilder;
import com.mygdx.game.ui.Font;
import com.mygdx.game.ui.actors.BaseActor;

/**
 * Created by Lee on 2016-06-16.
 */
public class ChatDialog extends BaseDialog {
    private Skin skin;
    private Label chatArea;
    private ScrollPane chatAreaPane;
    private TextField chatField;
    private StringBuilder stringBuilder;

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


        this.chatArea = new Label("", skin);
        this.chatArea.setFillParent(true);
        this.chatArea.setStyle(getLabelStyle(skin, contentFont));

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
        this.setModal(true);
        this.setMovable(false);

        this.stringBuilder = new StringBuilder();
        this.chatField.setDisabled(true);
        this.addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    if(chatField.isDisabled()) {
                        chatField.setDisabled(false);
                        ChatDialog.this.getStage().setKeyboardFocus(chatField);
                    }
                    else {
                        chatField.setDisabled(true);
                        chat();
                        chatField.setText("");
                    }
                }

                return super.keyDown(event, keycode);
            }
        });
    }


    public void chat(String message) {

    }

    private void chat() {
        String message = this.chatField.getText();
        if(message.equals("")) return;

        this.stringBuilder.append("\n").append(message);
        this.chatArea.setText(this.stringBuilder.toString());
    }
}
