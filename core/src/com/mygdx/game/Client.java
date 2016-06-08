package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.controller.Loading;
import com.mygdx.game.controller.LoginController;
import com.mygdx.game.ui.SystemMessage;
import network.Network;

/**
 * Created by Lee on 2016-05-18.
 */
public class Client extends ApplicationAdapter {
    private static Client instance;
    private GameController currentController;
    private GameController preController;

    public Client() { Client.instance = this; }

    public GameController getCurrentController() {
        return instance.currentController;
    }
    public static void changeCurrentController(GameController controller) {
        instance.currentController = controller;
    }


    @Override
    public void create() {
        this.currentController = new Loading(new LoginController(), "초기화 중입니다.");
        try {
            Network.getInstance().connect();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (this.preController != this.currentController) {
            if (this.preController != null) this.preController.dispose();
            if (this.currentController != null) this.currentController.create();
            this.preController = this.currentController;
        }
        if (this.currentController != null) this.currentController.render();
        SystemMessage.getInstance().render();
    }

    @Override
    public void dispose() {
        if (this.currentController != null) this.currentController.dispose();
    }
}
