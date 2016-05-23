package com.mygdx.game.manager;

import com.mygdx.game.object.Entity;

import java.util.LinkedList;

/**
 * Created by Lee on 2016-05-20.
 */
public class ObjectManager {
    private LinkedList<Entity> objects;

    public ObjectManager() {
        this.objects = new LinkedList<Entity>();
    }

    public void update() {
        for (Entity e : objects){
            e.update();
        }
    }

    public void add(Entity entity) {
        this.objects.add(entity);
    }
}
