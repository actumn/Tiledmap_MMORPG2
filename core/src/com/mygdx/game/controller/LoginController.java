package com.mygdx.game.controller;

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
import network.Network;

/**
 * Created by Lee on 2016-06-06.
 */
public class LoginController extends GameController {
    private Stage stage;
    private Skin skin;
    private SpriteBatch batch;

    /* UI Properties */
    private LoginTable table;


    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.stage = new Stage(new ScreenViewport());
        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        this.table = new LoginTable(skin);

        this.stage.addActor(table);

        Gdx.input.setInputProcessor(stage);;
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
        Font.getInstance().reloadFont();
    }

    private class LoginTable extends Table {
        /* ui properties */
        private TextField idField;
        private TextField pwField;
        private TextButton loginButton;
        private TextButton quitButton;
        private TextButton joinButton;
        private TextButton findPasswordButton;

        private LoginTable(Skin skin) {
            super(skin);

            final int titleFontSize = 23;
            BitmapFont titleFont = Font.getInstance().getFont(titleFontSize);

            Label loginLabel = new Label("로그인", skin);
            loginLabel.setStyle(getLabelStyle(skin, titleFont));

            final int labelFontSize = 16;
            BitmapFont contentFont = Font.getInstance().getFont(labelFontSize);

            Label idLabel = new Label("ID:", skin);
            idLabel.setStyle(getLabelStyle(skin, contentFont));
            Label pwLabel = new Label("PW:", skin);
            pwLabel.setStyle(getLabelStyle(skin, contentFont));

            idField = new TextField("", skin);
            idField.setStyle(getTextFieldStyle(skin, contentFont));
            idField.setMessageText("아이디");
            pwField = new TextField("", skin);
            pwField.setStyle(getTextFieldStyle(skin, contentFont));
            pwField.setMessageText("비밀번호");
            pwField.setPasswordMode(true);
            pwField.setPasswordCharacter('*');


            final float tableX = Gdx.graphics.getWidth() / 2 - getWidth() / 2;
            final float tableY = Gdx.graphics.getHeight() / 2 - getHeight() / 2 - 100f;
            this.setPosition(tableX, tableY);

            final float tablePadding = 10.0f;
            this.add(loginLabel).padBottom(tablePadding).colspan(2);
            this.row().pad(tablePadding);
            this.add(idLabel, idField);
            this.row();
            this.add(pwLabel, pwField);
            this.row().pad(tablePadding*2);

            loginButton = new TextButton("로그인", skin);
            loginButton.setStyle(getTextButtonStyle(skin, contentFont));

            quitButton = new TextButton("종료", skin);
            quitButton.setStyle(getTextButtonStyle(skin, contentFont));

            Table temp = new Table();
            temp.add(loginButton).width(60f).height(40f).padRight(5f);
            temp.add(quitButton).width(60f).height(40f);
            this.add(temp).colspan(2);
            this.row().padBottom(tablePadding);


            joinButton = new TextButton("회원 가입", skin);
            joinButton.setStyle(getTextButtonStyle(skin, contentFont));

            findPasswordButton = new TextButton("비밀번호 찾기", skin);
            findPasswordButton.setStyle(getTextButtonStyle(skin, contentFont));

            this.add(joinButton).fillX().fillY().colspan(2).row();
            this.add(findPasswordButton).fillX().fillY().colspan(2).row();


            loginButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("login");
                }
            });

            quitButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Network.getInstance().disconnect();
                    Gdx.app.exit();
                }
            });

            joinButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Client.changeCurrentController(new Loading(new JoinController()));
                }
            });

            findPasswordButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("find password");
                }
            });
        }
        private TextButton.TextButtonStyle getTextButtonStyle(Skin skin, BitmapFont font) {
            return new TextButton.TextButtonStyle(skin.getDrawable("default-round"), skin.getDrawable("default-round-down"), skin.getDrawable("default-round"), font);
        }

        private TextField.TextFieldStyle getTextFieldStyle(Skin skin, BitmapFont font) {
            return new TextField.TextFieldStyle(font, skin.getColor("white"), skin.getDrawable("cursor"), skin.getDrawable("selection"), skin.getDrawable("textfield"));
        }

        private Label.LabelStyle getLabelStyle(Skin skin, BitmapFont font) {
            return new Label.LabelStyle(font, skin.getColor("white"));
        }
    }


}