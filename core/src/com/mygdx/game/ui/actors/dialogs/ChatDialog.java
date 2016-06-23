package com.mygdx.game.ui.actors.dialogs;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.mygdx.game.object.Entity;
import com.mygdx.game.ui.Font;
import network.Network;
import protocol.Packet.PacketFactory;

import java.util.ArrayList;

/**
 * Created by Lee on 2016-06-16.
 */
public class ChatDialog extends BaseDialog {
    private Skin skin;
    private List chatList;
    private ScrollPane chatAreaPane;
    private TextField chatField;

    /* messages */
    private ArrayList<ChatMessage> chatMessages;

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


        this.chatList = new List(skin);
        this.chatList.setStyle(getListStyle(skin, contentFont));

        this.chatAreaPane = new ScrollPane(chatList, skin);
        this.chatAreaPane.setOverscroll(false, true);
        this.chatAreaPane.setSmoothScrolling(true);
        this.chatAreaPane.setFlickScroll(false);
        this.chatAreaPane.setPosition(horizontalPad / 2.0f, verticalPad / 2.0f + 35);
        this.chatAreaPane.setSize(width - horizontalPad, height - 65);


        this.chatField = new TextField("", skin);
        this.chatField.setStyle(getTextFieldStyle(skin, contentFont));
        this.chatField.setPosition(horizontalPad / 2.0f, verticalPad / 2.0f);
        this.chatField.setSize(width - horizontalPad, 30f);
        this.chatField.setStyle(getTextFieldStyle(skin, contentFont));

        this.addActor(this.chatAreaPane);
        this.addActor(chatField);
        this.setModal(true);
        this.setMovable(false);

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

        this.chatMessages = new ArrayList<>();
    }

    public void append(Entity entity, String message) {
        StringBuilder messageBuilder = new StringBuilder(message);
        int index = 0;
        while ((index += 40) < messageBuilder.length()) {
            messageBuilder.insert(index, '\n');
        }
        this.chatMessages.add(new ChatMessage(entity, messageBuilder.toString()));

        this.chatList.setItems(chatMessages.toArray());
        chatAreaPane.layout();
        chatAreaPane.scrollTo(0,0,0,0, true, true);
    }

    private void chat() {
        String message = chatField.getText();
        if (message.equals("") || message.length() == 0) return;

        PacketFactory packetFactory = Network.getInstance().getPacketFactory();
        Network.getInstance().send(packetFactory.chat(0, message));
    }

    private class ChatMessage {
        private Entity entity;
        private String message;

        ChatMessage(Entity entity, String message) {
            this.entity = entity;
            this.message = message;
        }

        @Override
        public String toString() {
            return entity + ": " + message;
        }
    }
}
