package com.mygdx.game.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.object.*;
import com.mygdx.game.object.Character;

import java.util.*;

/**
 * Created by Lee on 2016-05-20.
 */
public class ObjectManager {
    private SpriteBatch batch;
    private LinkedList<DrawObject> drawObjects;
    private LinkedList<Effect> effects;
    private Quadtree entities;
    private Rectangle mapRectangle;

    private DrawObjectComparator comparator = new DrawObjectComparator();
    private OrthographicCamera camera;
    private Character centerCharacter;

    public ObjectManager(){
        this(null);
    }
    public ObjectManager(Character centerCharacter) {
        this.batch = new SpriteBatch();
        this.drawObjects = new LinkedList<DrawObject>();
        this.effects = new LinkedList<Effect>();
        this.mapRectangle = new Rectangle();

        this.entities = new Quadtree(0, mapRectangle);

        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera.update();

        this.centerCharacter = centerCharacter;
    }
    public void setMapRectangle(int x, int y, int width, int height) {
        this.mapRectangle.set(x, y, width, height);
    }

    public void update() {
        List<Entity> entityList = new LinkedList<Entity>();
        entityList = entities.retrieve(entityList, mapRectangle);

        entities.clear();
        for (Entity entity : entityList){
            entity.update();
            entities.insert(entity);
        }
        entityList.clear();
        // Entity Update

        for(Effect effect: this.effects) {
            effect.update();
        }
        effectCollision();
        // Effect update


        // when effect ends
        Iterator<Effect> iter = effects.iterator();
        while(iter.hasNext()) {
            Effect effect = iter.next();

            if (effect.getEffectState() == Effect.EffectState.end)
                iter.remove();
        }


    }

    public void draw() {
        if (centerCharacter != null) {
            int x = this.centerCharacter.getDrawX() - (int) this.camera.position.x;
            int y = this.centerCharacter.getDrawY() - (int) this.camera.position.y;
            this.camera.translate(Math.min(10, x), Math.min(10, y));
        }

        Collections.sort(this.drawObjects, comparator);

        camera.update();
        this.batch.setProjectionMatrix(this.camera.combined);

        // Object rendering begin
        this.batch.begin();
        for(DrawObject object: this.drawObjects) {
            object.draw(this.batch);
        }
        this.batch.end();
        // Object rendering end

        // effects rendering begin
        this.batch.begin();
        for(Effect effect: this.effects) {
            effect.draw(this.batch);
        }
        this.batch.end();
        // effects rendering end
    }

    public void add(DrawObject object) {
        this.drawObjects.add(object);
    }

    public void add(Entity entity) {
        this.drawObjects.add(entity);
        this.entities.insert(entity);
    }

    public void add(Effect e) {
        e.setObjectManager(this);
        this.effects.add(e);
    }

    public void remove(Effect e) {
        Iterator<Effect> iter = effects.iterator();

        while(iter.hasNext()) {
            Effect effect = iter.next();

            if (effect == e)
                iter.remove();
        }
    }

    public void getNearestObject() {}

    public void effectCollision() {
        List<Entity> entityList = new LinkedList<Entity>();
        for (Effect effect: effects) {
            // rectangle effect
            if (effect instanceof RectableEffect) {
                rectEffectCollision(entityList, (RectableEffect) effect);
            }
        }
    }
    private void rectEffectCollision(List<Entity> entityList, RectableEffect effect) {
        Rectangle effectBounds = ((RectableEffect) effect).getBounds();
        entityList = entities.retrieve(entityList, effectBounds);

        for (Entity entity: entityList) {
            Rectangle entityBounds = entity.getBounds();

            if(effectBounds.overlaps(entityBounds)) {
                effect.work(entity);
            }
        }
    }

    public void setCenterCharacter(Character centerCharacter) {
        this.centerCharacter = centerCharacter;
    }

    class DrawObjectComparator implements Comparator<DrawObject> {
        @Override
        public int compare(DrawObject o1, DrawObject o2) {
            return  o2.getZ() - o1.getZ();
        }
    }
}
