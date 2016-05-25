package com.mygdx.game.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.object.Character;
import com.mygdx.game.object.DrawObject;
import com.mygdx.game.object.Entity;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Created by Lee on 2016-05-20.
 */
public class ObjectManager {
    private SpriteBatch batch;
    private LinkedList<DrawObject> objects;
    private LinkedList<Entity> entities;

    private DrawObjectComparator comparator = new DrawObjectComparator();
    private OrthographicCamera camera;

    private Character centerCharacter;

    public ObjectManager(SpriteBatch batch) {
        this(batch, null);
    }

    public ObjectManager(SpriteBatch batch, Character centerCharacter) {
        this.batch = batch;
        this.objects = new LinkedList<DrawObject>();
        this.entities = new LinkedList<Entity>();

        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera.update();

        this.centerCharacter = centerCharacter;
    }

    public void update() {
        for (Entity e : entities){
            e.update();
        }
    }

    public void draw() {
        if (centerCharacter != null) {
            int x = this.centerCharacter.getDrawX() - (int) this.camera.position.x;
            int y = this.centerCharacter.getDrawY() - (int) this.camera.position.y;
            this.camera.translate(Math.min(10, x), Math.min(10, y));
        }

        Collections.sort(this.objects, comparator);

        camera.update();
        this.batch.setProjectionMatrix(this.camera.combined);

        // Object rendering begin
        this.batch.begin();
        for(DrawObject object: this.objects) {
            object.draw(this.batch);
        }
        this.batch.end();
    }

    public void add(DrawObject object) {
        this.objects.add(object);
    }

    public void add(Entity entity) {
        this.objects.add(entity);
        this.entities.add(entity);
    }


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
