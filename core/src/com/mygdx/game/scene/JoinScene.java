package com.mygdx.game.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Client;
import com.mygdx.game.ui.Font;
import com.mygdx.game.ui.SystemMessage;
import com.mygdx.game.ui.dialog.BaseDialog;
import network.Network;
import org.json.simple.JSONObject;
import protocol.Packet.PacketFactory;

/**
 * Created by Lee on 2016-06-07.
 */
public class JoinScene extends GameScene {
    private SpriteBatch batch;
    private Stage stage;
    private Skin skin;

    @Override
    public void create() {
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        stage.addActor(new JoinDialog("", skin));
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Assets.getInstance().drawTitleScreen(batch, 0.6f);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    public String join(int job_id, String user_id, String user_name, String user_pw) {
        if (job_id == 0 || user_id.equals("") || user_name.equals("") || user_pw.equals(""))
            return "빈칸을 채워주세요";

        PacketFactory packetFactory = Network.getInstance().getPacketFactory();
        Network.getInstance().send(packetFactory.join(user_id, user_pw, user_name, job_id));

        long expiryTime = System.currentTimeMillis() + 4000;
        while(true) {
            if (expiryTime - System.currentTimeMillis() < 0) {
                return "네트워크 오류. 다시 시도해 주세요";
            }
            JSONObject recvPacket = Network.getInstance().pollPacket();

            if (recvPacket == null) continue;
            if (!recvPacket.get("type").equals("notify")) continue;
            String packetMessage = (String) recvPacket.get("content");
            return packetMessage;
        }
    }

    private class JoinDialog extends BaseDialog {
        private BitmapFont contentFont;
        private SelectBox selectRole;
        private Label idLabel, nameLabel, pwLabel;
        private TextField idField, nameField, pwField;
        private TextButton joinButton, backButton;

        public JoinDialog(String title, Skin skin) {
            super(title, skin);

            int contentFontSize = 16;
            contentFont = Font.getInstance().getFont(contentFontSize);

            this.setStyle(getWindowStyle(skin, contentFont));
            this.getTitleLabel().setText("회원가입");

            final float dialogWidth = 300f, dialogHeight = 280f;
            final float dialogX = Gdx.graphics.getWidth() / 2 - dialogWidth / 2;
            final float dialogY = Gdx.graphics.getHeight() / 2 - dialogHeight / 2;
            this.setSize(dialogWidth, dialogHeight);
            this.setPosition(dialogX, dialogY);
            this.setMovable(false);
            this.setResizable(false);


            final float selectX = 10f, selectY = dialogHeight - 60f;
            final float selectWidth = dialogWidth - 2*selectX;
            final float selectHeight = 30f;
            selectRole = new SelectBox(skin);
            selectRole.setStyle(getSelectBoxStyle(skin, contentFont));
            selectRole.getList().setStyle(getListStyle(skin, contentFont));
            selectRole.setPosition(selectX, selectY);
            selectRole.setSize(selectWidth, selectHeight);
            selectRole.setItems("직업 선택", "검사");
            selectRole.setSelected("직업 선택");

            final float idLabelX = 10f, idLabelY = selectY - 50f;
            final float idLabelWidth = 40f, idLabelHeight = 40f;
            idLabel = new Label("ID", skin);
            idLabel.setStyle(getLabelStyle(skin, contentFont));
            idLabel.setPosition(idLabelX, idLabelY);
            idLabel.setSize(idLabelWidth, idLabelHeight);

            final float idFieldX = 2 * idLabelX + idLabelWidth;
            final float idFieldY = idLabelY;
            final float idFieldWidth = dialogWidth - idFieldX - idLabelX, idFieldHeight = idLabelHeight;
            idField = new TextField("", skin);
            idField.setStyle(getTextFieldStyle(skin, contentFont));
            idField.setPosition(idFieldX, idFieldY);
            idField.setSize(idFieldWidth, idFieldHeight);


            final float nameLabelX = idLabelX, nameLabelY = idLabelY - 50f;
            final float nameLabelWidth = idLabelWidth, nameLabelHeight = idLabelHeight;
            nameLabel = new Label("NAME", skin);
            nameLabel.setStyle(getLabelStyle(skin, contentFont));
            nameLabel.setSize(nameLabelWidth, nameLabelHeight);
            nameLabel.setPosition(nameLabelX, nameLabelY);

            final float nameFieldX = idFieldX, nameFieldY = nameLabelY;
            final float nameFieldWidth = idFieldWidth, nameFieldHeight = idFieldHeight;
            nameField = new TextField("", skin);
            nameField.setStyle(getTextFieldStyle(skin, contentFont));
            nameField.setPosition(nameFieldX, nameFieldY);
            nameField.setSize(nameFieldWidth, nameFieldHeight);

            final float pwLabelX = nameLabelX, pwLabelY = nameLabelY - 50f;
            final float pwLabelWidth = idFieldWidth, pwLabelHeight = idFieldHeight;
            pwLabel = new Label("PW", skin);
            pwLabel.setStyle(getLabelStyle(skin, contentFont));
            pwLabel.setPosition(pwLabelX, pwLabelY);
            pwLabel.setSize(pwLabelWidth, pwLabelHeight);

            final float pwFieldX = idFieldX, pwFieldY = pwLabelY;
            final float pwFieldWidth = nameFieldWidth, pwFieldHeight = nameFieldHeight;
            pwField = new TextField("", skin);
            pwField.setPasswordMode(true);
            pwField.setPasswordCharacter('*');
            pwField.setStyle(getTextFieldStyle(skin, contentFont));
            pwField.setPosition(pwFieldX, pwFieldY);
            pwField.setSize(pwFieldWidth, pwFieldHeight);


            final float joinButtonX = 10f, joinButtonY = pwLabelY - 60f;
            final float joinButtonWidth = dialogWidth / 2 - joinButtonX*2;
            final float joinButtonHeight = 40f;
            joinButton = new TextButton("계정 생성", skin);
            joinButton.setStyle(getTextButtonStyle(skin, contentFont));
            joinButton.setPosition(joinButtonX, joinButtonY);
            joinButton.setSize(joinButtonWidth, joinButtonHeight);

            final float backButtonX = dialogWidth / 2 + 10f, backButtonY = joinButtonY;
            final float backButtonWidth = dialogWidth / 2 - joinButtonX*2;
            final float backButtonHeight = 40f;
            backButton = new TextButton("돌아가기", skin);
            backButton.setStyle(getTextButtonStyle(skin, contentFont));
            backButton.setPosition(backButtonX, backButtonY);
            backButton.setSize(backButtonWidth, backButtonHeight);





            joinButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    String response = join(selectRole.getSelectedIndex(), idField.getText(),
                            nameField.getText(), pwField.getText());

                    SystemMessage.getInstance().show(response, 2000, 16, 1.0f, 1.0f, 1.0f, 1.0f);
                    if (response.equals("회원가입 성공")) Client.changeCurrentController(new Loading(new LoginScene()));
                }
            });

            backButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Client.changeCurrentController(new Loading(new LoginScene()));
                }
            });

            this.addActor(selectRole);
            this.addActor(idLabel);
            this.addActor(nameLabel);
            this.addActor(pwLabel);
            this.addActor(idField);
            this.addActor(nameField);
            this.addActor(pwField);
            this.addActor(joinButton);
            this.addActor(backButton);
        }
    }

}
