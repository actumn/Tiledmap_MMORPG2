package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Client;
import com.mygdx.game.scene.TestScene;

/**
 * Created by Lee on 2016-06-09.
 */
public class TestLancher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1280;
        config.height = 720;
        config.useGL30 = false;
        config.resizable = false;

        new LwjglApplication(new TestScene(), config);
    }
}
