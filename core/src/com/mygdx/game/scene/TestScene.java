package com.mygdx.game.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.listener.CharacterInputListener;
import com.mygdx.game.map.Map;
import com.mygdx.game.object.Entity;
import com.mygdx.game.object.Player;
import com.mygdx.game.object.Mob;
import com.mygdx.game.ui.HealthBar;

/**
 * Created by Lee on 2016-06-09.
 */
public class TestScene extends GameScene {
    private Skin skin;
    private Stage stage;
    private CharacterInputListener characterInputListener;

    private Map m;

    @Override
    public void create() {
        this.stage = new Stage(new ScreenViewport());
        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

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

        System.out.println("fps: " + Gdx.graphics.getFramesPerSecond());
    }


    private void initMap() {
        this.m = new Map("test", "maps/sample.tmx");
        // Test code
        Mob mob = new Mob()
                .setMap(m)
                .stat(100, 0, 8, 0)
                .setName("baby dragon")
                .loadAnimation(Entity.EntityState.normal.getValue(), "npc-monsters", 0,  0);

        Player c = new Player()
                .setName("admin")
                .setMap(m)
                .loadAnimation(Entity.EntityState.normal.getValue(), "character-walk", 0, 0)
                .loadAnimation(Entity.EntityState.attacking.getValue(), "character-attack", 0, 0);


        this.characterInputListener = new CharacterInputListener(c);
        stage.addListener(this.characterInputListener);
        m.add(c);
        m.add(mob);
        m.setCenterCharacter(c);
    }
}