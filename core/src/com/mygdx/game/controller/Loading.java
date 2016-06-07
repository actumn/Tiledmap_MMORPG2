package com.mygdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Client;
import com.mygdx.game.ui.SystemMessage;

/**
 * Created by Lee on 2016-06-06.
 */
public class Loading extends GameController {
    private SpriteBatch batch;
    private GameController nextController;
    private String message;

    public Loading(GameController nextController, String message) {
        this.message = message;
        this.nextController = nextController;
    }

    public Loading(GameController nextController) {
        this(nextController, "잠시만 기다려주세요.");
    }

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        SystemMessage.getInstance().show(message, 2000, 16, 1.0f, 1.0f, 1.0f, 1.0f);

    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Assets.getInstance().drawTitleScreen(batch, 1.0f);

        Client.changeCurrentController(nextController);
    }
}
