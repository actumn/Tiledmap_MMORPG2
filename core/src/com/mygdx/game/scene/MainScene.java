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
import network.Network;
import org.json.simple.JSONObject;

/**
 * Created by Lee on 2016-06-06.
 */
public class MainScene extends GameScene {
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
    }


    private void initMap() {
        JSONObject characterPacket = null;
        JSONObject movePacket = null;
        while(characterPacket == null || movePacket == null) {
            JSONObject packet = Network.getInstance().pollPacket();
            if (packet.get("type").equals("character"))
                characterPacket = packet;
            if (packet.get("type").equals("move"))
                movePacket = packet;
        }
        this.m = new Map("test", "maps/sample.tmx");
        // Test code
        Mob mob;

        Player c = new Player()
                .setName("admin")
                .setMap(m)
                .loadAnimation(Entity.EntityState.normal.getValue(), "character-walk", 0, 0)
                .loadAnimation(Entity.EntityState.attacking.getValue(), "character-attack", 0, 0);

        this.characterInputListener = new CharacterInputListener(c);
        stage.addListener(this.characterInputListener);
        m.add(c);
        m.setCenterCharacter(c);
    }
}
