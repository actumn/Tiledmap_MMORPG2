package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.listener.CharacterInputListener;
import com.mygdx.game.map.Map;
import com.mygdx.game.object.Character;
import com.mygdx.game.object.Effect;
import com.mygdx.game.object.Mob;
import com.mygdx.game.object.RectableEffect;
import com.mygdx.game.ui.HealthBar;

/**
 * Created by Lee on 2016-05-18.
 */
public class Client extends ApplicationAdapter {
    private Skin skin;
    private Stage stage;
    private CharacterInputListener characterInputListener;

    private Map m;

    @Override
    public void create() {
        this.stage = new Stage(new ScreenViewport());
        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        this.m = new Map("maps/sample.tmx", skin);
        // Test code
        Character c;
        Effect effect;
        Mob mob;

        c = new Character()
                .setName("admin")
                .setMap(m)
                .loadAnimation(0,0);
        //mob = new Mob().setMap(m).loadAnimation();

        this.characterInputListener = new CharacterInputListener(c);
        stage.addListener(this.characterInputListener);
        m.add(c);
        //m.add(mob);
        m.setCenterCharacter(c);

        stage.addActor(new HealthBar());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        characterInputListener.update();
        this.m.update();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }


}
