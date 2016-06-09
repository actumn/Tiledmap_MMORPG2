package com.mygdx.game.map;

/**
 * Created by Lee on 2016-06-09.
 */
public class MapMovePoint {
    private int x, y;
    private int hashCoefficient;
    public MapMovePoint(int x, int y, int hashCoefficient) {
        this.x = x;
        this.y = y;
        this.hashCoefficient = hashCoefficient;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof MapMovePoint)) return false;
        MapMovePoint movePoint = (MapMovePoint) obj;
        return movePoint.x == this.x && movePoint.y == this.y;
    }

    @Override
    public int hashCode() {
        return x + hashCoefficient * y;
    }
}
