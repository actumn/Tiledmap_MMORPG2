package com.mygdx.game.map;

/**
 * Created by Lee on 2016-06-09.
 */
public class MapMoveDestination {
    private int newMapId;
    private int newX;
    private int newY;

    public MapMoveDestination(int newMapId, int newX, int newY) {
        this.newMapId = newMapId;
        this.newX = newX;
        this.newY = newY;
    }

    public int getNewMapId() {
        return newMapId;
    }

    public int getNewX() {
        return newX;
    }

    public int getNewY() {
        return newY;
    }
}
