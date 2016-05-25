package com.mygdx.game.map;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.object.Rectable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2016-05-24.
 */
public class Quadtree {
    private int MAX_OBJECTS = 10;
    private int MAX_LEVELS = 5;

    private int level;
    private List<Rectable> objects;
    private Rectangle bounds;
    private Quadtree[] nodes;


    /*
        Constructor
     */
    public Quadtree(int pLevel, Rectangle pBounds) {
        this.level = pLevel;
        this.objects = new ArrayList();
        this.bounds = pBounds;
        this.nodes = new Quadtree[4];
    }

    /*
        Clears the quadtree
     */
    public void clear() {
        this.objects.clear();

        for (int i = 0; i< nodes.length; i++) {
            if(nodes[i] != null) {
                nodes[i].clear();
                nodes[i] = null;
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

        int nextLevel = this.level + 1;
        nodes[0] = new Quadtree(nextLevel, new Rectangle(x + subWidth, y, subWidth, subHeight));
        nodes[1] = new Quadtree(nextLevel, new Rectangle(x, y, subWidth, subHeight));
        nodes[2] = new Quadtree(nextLevel, new Rectangle(x, y + subHeight, subWidth, subHeight));
        nodes[3] = new Quadtree(nextLevel, new Rectangle(x + subWidth, y + subHeight, subWidth, subHeight));
    }

    /*
        Determine which node the object belongs to.
        -1 means object cannot completely fit within a child node and is part of the parent node
     */
    private int getIndex(Rectangle pRect) {
        int index = -1;
        double verticalMidpoint = bounds.getX() + (bounds.getWidth() / 2);
        double horizontalMidpoint = bounds.getY() + (bounds.getHeight() / 2);

        // Object can completely fit within the top quadrants
        boolean topQuadrant = (pRect.getY() < horizontalMidpoint
                && pRect.getY() + pRect.getHeight() < horizontalMidpoint);
        // Object can completely fit within the bottom quadrants
        boolean bottomQuadrant = (pRect.getY() > horizontalMidpoint);

        // Object can completely fit within the left quadrants
        if (pRect.getX() < verticalMidpoint && pRect.getX() + pRect.getWidth() < verticalMidpoint) {
            if (topQuadrant) {
                index = 1;
            }
            else if (bottomQuadrant) {
                index = 2;
            }
        }
        // Obejct can completely fit within the right quadrants
        else if (pRect.getX() > verticalMidpoint) {
            if (topQuadrant) {
                index = 0;
            }
            else if (bottomQuadrant) {
                index = 3;
            }
        }

        return index;
    }

    /*
        insert the object into the quadtree.
        If the node exceeds the capacity, it will split and add all objects to their corresponding nodes.
     */
    public void insert(Rectable rectable) {
        Rectangle pRect = rectable.getRectangle();

        if (nodes[0] != null) {
            int index = getIndex(pRect);

            if (index != -1) {
                nodes[index].insert(rectable);

                return;
            }
        }

        objects.add(rectable);

        if (objects.size() > MAX_OBJECTS && level < MAX_LEVELS) {
            if (nodes[0] == null) {
                split();
            }

            int i = 0;
            while (i < objects.size()) {
                int index = getIndex(objects.get(i).getRectangle());

                if (index != -1) {
                    nodes[index].insert(objects.remove(i));
                }
                else {
                    i++;
                }
            }
        }
    }

    /*
        Return all objects that could coolide with the given object
     */
    public List retrieve(List returnObjects, Rectable rectable) {
        int index = getIndex(rectable.getRectangle());
        if (index != -1 && nodes[0] != null) {
            nodes[index].retrieve(returnObjects, rectable);
        }

        returnObjects.addAll(objects);

        return returnObjects;
    }
}
