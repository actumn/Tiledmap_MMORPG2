package com.mygdx.game.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.XmlDataLoader;
import com.mygdx.game.listener.CharacterInputListener;
import com.mygdx.game.map.Map;
import com.mygdx.game.object.Entity;
import com.mygdx.game.object.NPC;
import com.mygdx.game.object.Player;
import com.mygdx.game.object.Mob;
import com.mygdx.game.ui.HealthBar;

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
    private Stage stage;

    // Controller
    private CharacterInputListener characterInputListener;

    @Override
    public void create() {
        this.stage = new Stage(new ScreenViewport());
        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        this.xmlDataLoader = new XmlDataLoader();
        initMap();


        stage.addActor(new HealthBar());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        characterInputListener.update();
        this.m.update();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }


    private void initMap() {
        //this.m = new Map("test", "maps/starting.tmx");
        try {
            this.m = this.xmlDataLoader.loadMap(1);

            Player c = this.xmlDataLoader.loadPlayer(1)
                    .level(1)
                    .setName("adm\nin")
                    .setMap(m);
            m.add(c);
            m.setCenterCharacter(c);

            this.characterInputListener = new CharacterInputListener(c);
            stage.addListener(this.characterInputListener);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}