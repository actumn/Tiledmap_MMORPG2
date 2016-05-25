package com.mygdx.game.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.object.Character;
import com.mygdx.game.object.DrawObject;
import com.mygdx.game.object.Effect;

import java.util.LinkedList;

/**
 * Created by Lee on 2016-05-22.
 */
public class EffectManager {
    private SpriteBatch batch;
    private LinkedList<Effect> effects;


    public EffectManager(SpriteBatch batch) {
        this.batch = batch;
        this.effects =  new LinkedList<Effect>();
    }

    public void add(Effect e) { this.effects.add(e); }


    public void draw() {
        batch.begin();
        for(Effect effect: this.effects) {
            effect.draw(this.batch);
        }
        this.batch.end();
        // Object rendering end
    }
}
