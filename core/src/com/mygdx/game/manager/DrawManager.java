package com.mygdx.game.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.object.Character;
import com.mygdx.game.object.DrawObject;
import com.mygdx.game.object.Effect;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Created by Lee on 2016-05-19.
 */
public class DrawManager {
    private SpriteBatch batch;
    private LinkedList<DrawObject> drawObjects;
    private LinkedList<Effect> effects;
    private OrthographicCamera camera;
    private DrawObjectComparator comparator = new DrawObjectComparator();

    private Character centerCharacter;

    public DrawManager() {
        this(null);
    }
    public DrawManager(Character centerCharacter) {
        this.drawObjects  = new LinkedList<DrawObject>();
        this.effects =  new LinkedList<Effect>();

        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera.update();

        this.centerCharacter = centerCharacter;
    }

    public void draw() {
        int x = this.centerCharacter.getDrawX() - (int)this.camera.position.x;
        int y = this.centerCharacter.getDrawY() - (int)this.camera.position.y;
        this.camera.translate(Math.min(10, x), Math.min(10, y));

        Collections.sort(this.drawObjects, comparator);

        camera.update();
        this.batch.setProjectionMatrix(this.camera.combined);

        // Object rendering begin
        this.batch.begin();
        for(DrawObject object: this.drawObjects) {
            object.draw(this.batch);
        }
        for(Effect effect: this.effects) {
            effect.draw(this.batch);
        }
        this.batch.end();
        // Object rendering end
    }

    public void add(DrawObject object) {
        this.drawObjects.add(object);
    }
    public void add(Effect e) { this.effects.add(e); }


    public void setCenterCharacter(Character centerCharacter) {
        this.centerCharacter = centerCharacter;
    }

    class DrawObjectComparator implements Comparator<DrawObject> {
        @Override
        public int compare(DrawObject o1, DrawObject o2) {
            return  o1.getZ() - o2.getZ();
        }
    }
}
