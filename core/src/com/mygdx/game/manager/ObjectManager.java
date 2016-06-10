package com.mygdx.game.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.object.*;
import com.mygdx.game.object.Player;

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

    private OrthographicCamera camera;
    private Player centerPlayer;

    public ObjectManager(){
        this(null);
    }
    public ObjectManager(Player centerPlayer) {
        this.batch = new SpriteBatch();
        this.drawObjects = new LinkedList<DrawObject>();
        this.effects = new LinkedList<Effect>();
        this.mapRectangle = new Rectangle();

        this.entities = new Quadtree(0, mapRectangle);

        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera.update();

        this.centerPlayer = centerPlayer;
    }
    public void setMapRectangle(int x, int y, int width, int height) {
        this.mapRectangle.set(x, y, width, height);
    }

    public void update() {
        List<Entity> entityList = new LinkedList<>();
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

    private void effectCollision() {
        List<Entity> entityList = new LinkedList<>();
        // rectangle effect
        effects.stream().filter(effect -> effect instanceof RectableEffect).forEach(effect -> {
            rectEffectCollision(entityList, (RectableEffect) effect);
        });
    }


    public void draw() {
        if (centerPlayer != null) {
            int x = this.centerPlayer.getDrawX() - (int) this.camera.position.x;
            int y = this.centerPlayer.getDrawY() - (int) this.camera.position.y;
            this.camera.translate(Math.min(10, x), Math.min(10, y));
        }

        Collections.sort(this.drawObjects, (o1, o2) -> o2.getZ() - o1.getZ());

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




    public void getNearestObject() {}

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

    public void setCenterPlayer(Player centerPlayer) {
        this.centerPlayer = centerPlayer;
    }
}
