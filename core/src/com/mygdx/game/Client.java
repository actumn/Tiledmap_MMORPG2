package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.scene.GameScene;
import com.mygdx.game.scene.Loading;
import com.mygdx.game.scene.LoginScene;
import com.mygdx.game.ui.SystemMessage;
import network.Network;

/**
 * Created by Lee on 2016-05-18.
 */
public class Client extends ApplicationAdapter {
    private static Client instance;
    private GameScene currentController;
    private GameScene preController;

    public Client() { Client.instance = this; }

    public GameScene getCurrentController() {
        return instance.currentController;
    }
    public static void changeCurrentController(GameScene controller) {
        instance.currentController = controller;
    }


    @Override
    public void create() {
        this.currentController = new Loading(new LoginScene(), "초기화 중입니다.");
        try {
            Network.getInstance().connect();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Gdx.app.exit();
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
