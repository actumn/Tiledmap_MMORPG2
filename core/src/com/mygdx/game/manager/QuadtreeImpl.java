package com.mygdx.game.manager;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.object.Rectable;

import java.util.List;

/**
 * Created by Lee on 2016-06-19.
 */
public interface QuadtreeImpl {
    void clear();
    void insert(Rectable rectable);
    List retrieve(java.util.List returnObjects, Rectangle pRect);
}
