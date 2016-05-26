package com.mygdx.game.object;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Lee on 2016-05-26.
 */
public class RectableEffect extends Effect implements Rectable {
    private Rectangle bounds;

    public RectableEffect(String effectName) {
        super(effectName);
        this.bounds = new Rectangle(this.getDrawX(), this.getDrawY(),
                this.getTextureRegion().getRegionWidth(), this.getTextureRegion().getRegionHeight());
    }

    @Override
    public void setXY(int x, int y) {
        super.setXY(x, y);
        this.bounds.setPosition(this.getDrawX(), this.getDrawY());
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }
}
