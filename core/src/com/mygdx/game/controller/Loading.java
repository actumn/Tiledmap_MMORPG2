package com.mygdx.game.controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.ui.SystemMessage;

/**
 * Created by Lee on 2016-06-06.
 */
public class Loading extends GameController {
    private SpriteBatch batch;
    private GameController nextController;
    private String message;

    public Loading(Class nextControllerClass, String message) {
        try {
            this.message = message;
            this.nextController = (GameController) nextControllerClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        SystemMessage.getInstance().show(message, 2000, 16, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public void render() {
        Assets.getInstance().drawTitleScreen(batch);

    }
}
