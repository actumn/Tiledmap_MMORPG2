package com.mygdx.game.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Client;
import com.mygdx.game.XmlDataLoader;
import com.mygdx.game.listener.CharacterInputListener;
import com.mygdx.game.map.Map;
import com.mygdx.game.object.Entity;
import com.mygdx.game.object.Player;
import com.mygdx.game.ui.actors.HealthBar;
import com.mygdx.game.ui.SystemMessage;
import network.Network;
import org.json.simple.JSONObject;

import java.io.IOException;

/**
 * Created by Lee on 2016-06-06.
 */
public class MainScene extends GameScene {

    // Model
    private XmlDataLoader xmlDataLoader;
    private Map m;

    // View
    private Skin skin;
    private Stage gameStage;
    private Stage chatStage;
    private Stage escStage;

    // Controller
    private CharacterInputListener characterInputListener;



    @Override
    public void create() {
        // View
        ScreenViewport screenViewport = new ScreenViewport();
        this.gameStage = new Stage(screenViewport);
        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // model
        this.xmlDataLoader = new XmlDataLoader();
        try {
            initMap();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Control
        gameStage.addActor(new HealthBar());
        Gdx.input.setInputProcessor(gameStage);
    }

    @Override
    public void render() {
        updateNetwork();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        characterInputListener.update();
        this.m.update();

        gameStage.act(Gdx.graphics.getDeltaTime());
        gameStage.draw();
    }


    private void initMap() throws Exception {
        JSONObject playerPacket = null;
        JSONObject movePacket = null;

        long expiryTime = System.currentTimeMillis();
        while(playerPacket == null || movePacket == null) {
            if (System.currentTimeMillis() - expiryTime > 5000) {
                // will throw something
                SystemMessage.getInstance().show("네트워크 오류", 2000, 12, 1.0f, 1.0f, 1.0f, 1.0f);
            }

            JSONObject packet = Network.getInstance().pollPacket();
            if (packet == null) continue;
            if (packet.get("type").equals("character"))
                playerPacket = packet;
            if (packet.get("type").equals("move"))
                movePacket = packet;
        }

        long entityId = (long) playerPacket.get("id");
        String name = (String) playerPacket.get("name");
        int level = (int)(long) playerPacket.get("level");
        int job_id = (int)(long) playerPacket.get("job_id");


        long targetEntityId = (long) movePacket.get("id");
        int destMapId = (int)(long) movePacket.get("dest_map_id");
        int destX = (int)(long) movePacket.get("dest_x");
        int destY = (int)(long) movePacket.get("dest_y");


        this.m = this.xmlDataLoader.loadMap(destMapId);
        Player c = this.xmlDataLoader.loadPlayer(job_id)
                .level(level)
                .setName(name)
                .entityId(entityId)
                .setMap(m)
                .xy(destX, destY);

        this.characterInputListener = new CharacterInputListener(c);
        gameStage.addListener(this.characterInputListener);
        m.add(c);
        m.setCenterCharacter(c);
        System.out.println(entityId);
    }

    private void updateNetwork() {
        while (Client.getCurrentController() == this) {
            JSONObject packet = (JSONObject) Network.getInstance().pollPacket();
            if (packet == null) return;

            if (packet.get("type").equals("character")) {
                long entityId = (long) packet.get("id");
                String name = (String) packet.get("name");
                int level = (int)(long) packet.get("level");
                int job_id = (int)(long) packet.get("job_id");

                System.out.println("Character id : " + entityId);
                try {
                    Player c = this.xmlDataLoader.loadPlayer(job_id)
                            .level(level)
                            .setName(name)
                            .entityId(entityId)
                            .setMap(m);
                    m.add(c);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            else if (packet.get("type").equals("move")) {
                long entityId = (long) packet.get("id");
                int destMapId = (int)(long) packet.get("dest_map_id");
                int destX = (int)(long) packet.get("dest_x");
                int destY = (int)(long) packet.get("dest_y");

                Entity e = this.m.getEntityById(entityId);

                if (destMapId != this.m.getMapId()) {
                    this.m.remove(e);
                }
                else {
                    e.show_move(e.x, e.y, destX, destY);
                }
            }
        }
    }
}
