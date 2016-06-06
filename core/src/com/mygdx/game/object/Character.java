package com.mygdx.game.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.map.Map;
import com.mygdx.game.ui.Font;

/**
 * Created by Lee on 2016-05-19.
 */
public class Character extends Entity {
    private int nameWidth;
    private Map map;
    public final int speedX = 5;
    public final int speedY = 5;


    private long skillCoolDown = 0;

    public Character() {
        this.x = 100;
        this.y = 100;
        hp = maxHp = 100;
        entityState = EntityState.normal;

        team = 0;
    }
    public Character setName(String name) {
        this.name = name;
        this.nameWidth = (int)(Font.getInstance().getFont(12).getSpaceWidth() * this.name.length() * 2);
        return this;
    }
    public Character setMap(Map map) {
        this.map = map;
        return this;
    }
    public Character loadAnimation() {
        return loadAnimation(0, 0);
    }
    public Character loadAnimation(int index) {
        final int horizontalCharactersCount = 4;
        int iIndex = index / horizontalCharactersCount;
        int jIndex = index % horizontalCharactersCount;

        return loadAnimation(iIndex, jIndex);
    }
    public Character loadAnimation(int iIndex, int jIndex) {
        entityAnimation = new EntityAnimation(this)
                .loadAnimation();


        this.shapeRenderer = new ShapeRenderer();

        this.bounds = new Rectangle(getDrawX(), getDrawY(),
                this.getTextureRegion().getRegionWidth(), this.getTextureRegion().getRegionHeight());

        return this;
    }


    @Override
    public int getZ() {
        return this.y - 64;
    }

    public void skillAttack(){
        if (System.currentTimeMillis() <= skillCoolDown) return;

        Effect effect = new RectableEffect("effects/blue_crystal.png");

        double stand = ((double)direction - 1.5) * 2;
        /*  stand = north : 3
                    east : 1
                    west : -1
                    south : -3
        */
        int skillX = (int)stand % 3;
        int skillY = (int)stand / 3;
        /*
            x,y =   north : 0,1
                    east : 1,0
                    west : -1,0
                    south : 0, -1
         */
        final int distance = 70;
        skillX *= distance; skillY *= distance;

        effect.setXY(this.x + skillX, this.y + skillY);

        map.add(effect);

        skillCoolDown = System.currentTimeMillis() + 2000;
    }

    @Override
    public void move(int sx, int sy, int dx, int dy) {
        if (entityState == EntityState.attacking || map.checkCollision(dx, dy)) return;
        show_move(sx, sy, dx, dy);
    }

    @Override
    public void show_move(int sx, int sy, int dx, int dy) {
        this.x = dx;
        this.y = dy;

        int ax = dx - sx;
        int ay = dy - sy;

        if(ay<0) this.direction = DIRECTION.south.getValue();
        if(ax<0) this.direction = DIRECTION.west.getValue();
        if(ax>0) this.direction = DIRECTION.east.getValue();
        if(ay>0) this.direction = DIRECTION.north.getValue();

        this.bounds.setPosition(this.getDrawX(), this.getDrawY());
    }

    @Override
    public void action() {

    }

    @Override
    public void attack() {
        this.entityState = EntityState.attacking;

        s_attack = 0;
        s_direction = 0;
    }

    @Override
    public void draw(SpriteBatch batch) {
        entityAnimation.draw(batch);
        batch.end();

        // hp bar
        this.shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.shapeRenderer.setColor(0.8f, 0.0f, 0.0f, 1.0f);
        this.shapeRenderer.rect(
                getDrawX() + getTextureRegion().getRegionWidth() / 2 - 10, this.getDrawY() - 2, getPercentHp() / 5, 5
        );
        this.shapeRenderer.end();

        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        this.shapeRenderer.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        this.shapeRenderer.rect(
                this.getDrawX() + this.getTextureRegion().getRegionWidth() / 2 - 10,
                this.getDrawY() - 2,
                20, 5
        );
        this.shapeRenderer.end();

        batch.begin();
        Font font = Font.getInstance();

        font.getFont(12).setColor(1.0f, 1.0f, 1.0f, 1.0f);
        font.getFont(12).draw(batch, this.name,
                this.getDrawX() + this.getTextureRegion().getRegionWidth() / 2 - this.nameWidth / 2.0f,
                this.getDrawY() + this.getTextureRegion().getRegionHeight() + font.getFont(12).getCapHeight());
    }

    public void update() {
        this.entityAnimation.update();

        this.dx = this.x;
        this.dy = this.y;
    }

    public int getDrawX() {
        return this.x - this.getTextureRegion().getRegionWidth() / 2;
    }
    public int getDrawY() {
        return this.y;
    }
    public TextureRegion getTextureRegion() {
        return entityAnimation.getTextureRegion();
    }

    @Override
    public String toString() {
        return name;
    }

}
