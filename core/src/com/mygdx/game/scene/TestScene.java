package com.mygdx.game.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.XmlDataLoader;
import com.mygdx.game.listener.CharacterInputListener;
import com.mygdx.game.map.Map;
import com.mygdx.game.object.Player;
import com.mygdx.game.ui.actors.StateActor;
import com.mygdx.game.ui.dialog.ChatDialog;

import java.io.IOException;

/**
 * Created by Lee on 2016-06-09.
 */
public class TestScene extends GameScene {

    // Model
    private XmlDataLoader xmlDataLoader;
    private Map m;

    // View
    private Skin skin;
    private Stage gameStage;
    private Stage escStage;

    // Controller
    private CharacterInputListener characterInputListener;

    @Override
    public void create() {
        ScreenViewport screenViewport = new ScreenViewport();
        this.gameStage = new Stage(screenViewport);
        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        this.xmlDataLoader = new XmlDataLoader();
        initMap();


        gameStage.addActor(new StateActor(m.getCenterPlayer()));
        gameStage.addActor(new ChatDialog("", skin));
        Gdx.input.setInputProcessor(gameStage);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        characterInputListener.update();
        this.m.update();

        gameStage.act(Gdx.graphics.getDeltaTime());
        gameStage.draw();
    }


    private void initMap() {
        //this.m = new Map("test", "maps/starting.tmx");
        try {
            this.m = this.xmlDataLoader.loadMap(1);

            Player c = this.xmlDataLoader.loadPlayer(1)
                    .level(1)
                    .setName("admin")
                    .setMap(m);
            m.add(c);
            m.setCenterPlayer(c);

            this.characterInputListener = new CharacterInputListener(c);
            gameStage.addListener(this.characterInputListener);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}