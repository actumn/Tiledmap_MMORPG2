package com.mygdx.game.map;

import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2016-05-23.
 *
 * Reference
 * http://gamedevelopment.tutsplus.com/tutorials/quick-tip-use-quadtrees-to-detect-likely-collisions-in-2d-space--gamedev-374
 */
public class Quadtree {
    private int MAX_OBJECTS = 10;
    private int MAX_LEVELS = 5;

    private int level;
    private List objects;
    private Rectangle bounds;
    private Quadtree[] nodes;

    /*
        constructor
     */
    public Quadtree(int pLevel, Rectangle pBounds) {
        level = pLevel;
        objects = new ArrayList();
        bounds = pBounds;
        nodes = new Quadtree[4];
    }

    /*
        Clear the quadtree
     */
    public void clear() {
        objects.clear();

        for (Quadtree qt: nodes) {
            if (qt != null) {
                qt.clear();
                qt = null;
            }
        }
    }

    /*
        Splits the node into 4 subnodes
     */
    private void split() {
        int subWidth = (int)(bounds.getWidth() / 2);
        int subHeight = (int)(bounds.getHeight() / 2);
        int x = (int)bounds.getX();
        int y = (int)bounds.getY();

        nodes[0] = new Quadtree(level+1, new Rectangle(x + subWidth, y, subWidth, subHeight));
        nodes[1] = new Quadtree(level+1, new Rectangle(x, y, subWidth, subHeight));
        nodes[2] = new Quadtree(level+1, new Rectangle(x, y + subHeight, subWidth, subHeight));
        nodes[3] = new Quadtree(level+1, new Rectangle(x + subWidth, y + subHeight, subWidth, subHeight));
    }

    /*
        Determine which node the object belongs to -1 means
     */
}
