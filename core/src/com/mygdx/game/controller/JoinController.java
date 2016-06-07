package com.mygdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Client;
import com.mygdx.game.ui.Font;
import com.mygdx.game.ui.dialog.BaseDialog;

/**
 * Created by Lee on 2016-06-07.
 */
public class JoinController extends GameController {
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

    private class JoinDialog extends BaseDialog {
        private BitmapFont contentFont;
        private SelectBox selectRole;
        private Label idLabel, emailLabel, pwLabel;
        private TextField idField, emailField, pwField;
        private TextButton createAccountButton, backButton;

        public JoinDialog(String title, Skin skin) {
            super(title, skin);

            contentFont = Font.getInstance().getFont(12);

            this.setStyle(getWindowStyle(skin, contentFont));
            this.getTitleLabel().setText("회원가입");
            this.setSize(190f, 205f);
            this.setPosition(Gdx.graphics.getWidth()/2 - 95f, Gdx.graphics.getHeight()/2 - 100f);
            this.setMovable(false);
            this.setResizable(false);

            selectRole = new SelectBox(skin);
            selectRole.setStyle(getSelectBoxStyle(skin, contentFont));
            selectRole.getList().setStyle(getListStyle(skin, contentFont));
            selectRole.setSize(170f, 25f);
            selectRole.setPosition(10f, 150f);
            selectRole.setItems("직업 선택", "마법사", "궁수", "검사");
            selectRole.setSelected("직업 선택");

            idLabel = new Label("", skin);
            idLabel.setText("NAME");
            idLabel.setStyle(getLabelStyle(skin, contentFont));
            idLabel.setSize(40f, 20f);
            idLabel.setPosition(10f, 120f);

            emailLabel = new Label("EMAIL", skin);
            emailLabel.setText("EMAIL");
            emailLabel.setStyle(getLabelStyle(skin, contentFont));
            emailLabel.setSize(40f, 20f);
            emailLabel.setPosition(10f, 85f);

            pwLabel = new Label("PW", skin);
            pwLabel.setText("PW");
            pwLabel.setStyle(getLabelStyle(skin, contentFont));
            pwLabel.setSize(40f, 20f);
            pwLabel.setPosition(10f, 50f);

            emailField = new TextField("", skin);
            emailField.setStyle(getTextFieldStyle(skin, contentFont));
            emailField.setSize(130f, 25f);
            emailField.setPosition(50f, 115f);

            idField = new TextField("", skin);
            idField.setStyle(getTextFieldStyle(skin, contentFont));
            idField.setSize(130f, 25f);
            idField.setPosition(50f, 80f);

            pwField = new TextField("", skin);
            pwField.setPasswordMode(true);
            pwField.setPasswordCharacter('*');
            pwField.setStyle(getTextFieldStyle(skin, contentFont));
            pwField.setSize(130f, 25f);
            pwField.setPosition(50f, 45f);

            createAccountButton = new TextButton("", skin);
            createAccountButton.setStyle(getTextButtonStyle(skin, contentFont));
            createAccountButton.setText("계정 생성");
            createAccountButton.setSize(80f, 25f);
            createAccountButton.setPosition(100f, 10f);

            backButton = new TextButton("", skin);
            backButton.setStyle(getTextButtonStyle(skin, contentFont));
            backButton.setText("돌아가기");
            backButton.setSize(80f, 25f);
            backButton.setPosition(10f, 10f);

            selectRole.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    System.out.println(selectRole.getSelected());
                }
            });

            createAccountButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                }
            });

            backButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                    Client.changeCurrentController(new Loading(new LoginController()));
                }
            });

            this.addActor(selectRole);
            this.addActor(idLabel);
            this.addActor(emailLabel);
            this.addActor(pwLabel);
            this.addActor(idField);
            this.addActor(emailField);
            this.addActor(pwField);
            this.addActor(createAccountButton);
            this.addActor(backButton);
        }
    }

}
