package com.mygdx.game.object;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.manager.ObjectManager;

/**
 * Created by Lee on 2016-05-20.
 */
public class Effect implements DrawObject {
    private String effectName;
    private int x, y;
    private int index;
    private Animation effectAnimation;
    private ShapeRenderer shapeRenderer;
    private ObjectManager objectManager;

    private EffectState effectState;

    public Effect(String effectName) {
        this.effectName = effectName;
        this.x = 200;
        this.y = 200;
        this.index = 0;

        final int Horizontal_Count = 5;
        final int Vertical_Count = 7;
        final float Duration = 0.8f;

        // load texture
        Texture walkSheet = new Texture(Gdx.files.internal("effects/blue_crystal.png"));

        // split texture and load sprites
        TextureRegion[][] tmp = TextureRegion.split(
                walkSheet, walkSheet.getWidth() / Horizontal_Count,
                walkSheet.getHeight() / Vertical_Count
        );
        TextureRegion[] effectFrames = new TextureRegion[Horizontal_Count * Vertical_Count];

        // save sprites
        int index = 0;
        final int iOffsetBegin = 0;
        final int jOffsetBegin = 0;
        for (int i = iOffsetBegin; i < iOffsetBegin + Vertical_Count; i++) {
            for (int j = jOffsetBegin; j < jOffsetBegin + Horizontal_Count; j++) {
                effectFrames[index++] = tmp[i][j];
            }
        }
        this.effectAnimation = new Animation(Duration, effectFrames);
        this.shapeRenderer = new ShapeRenderer();

        effectState = EffectState.begin;
    }
    public void setXY(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void work(Entity entity) {
        if( index % 15 == 0 )
            // Need to be changed
            System.out.println(this + " overlaps " + entity);

    }

    @Override
    public int getZ() {
        return 0;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(this.getTextureRegion(), this.getDrawX(), this.getDrawY());
        batch.end();

        // hp bar
        this.shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        this.shapeRenderer.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        this.shapeRenderer.rect(
                this.getDrawX(),
                this.getDrawY(),
                this.getTextureRegion().getRegionWidth(),
                this.getTextureRegion().getRegionHeight()
        );
        this.shapeRenderer.end();

        batch.begin();
    }

    public void update(){
        if(effectState != EffectState.end) {
            effectState = EffectState.working;
            index += 1;
            if (index >= effectAnimation.getKeyFrames().length)
                effectState = EffectState.end;
        }
    }

    public int getDrawX() {
        return this.x - this.getTextureRegion().getRegionWidth() / 2;
    }
    public int getDrawY() {
        return this.y;
    }

    public TextureRegion getTextureRegion() {
        return this.effectAnimation.getKeyFrame(
                this.index,
                false
        );
    }

    public EffectState getEffectState(){
        return this.effectState;
    }

    public void setObjectManager(ObjectManager objectManager) {
        this.objectManager = objectManager;
    }

    private static class EffectTextureLoader {
        private static EffectTextureLoader instance = new EffectTextureLoader();
        private Texture effectSheet;
        private EffectTextureLoader() {
            this.effectSheet = new Texture(Gdx.files.internal("effects/blue_crystal.png"));
        }

    }

    @Override
    public String toString() {
        return effectName;
    }

    public enum EffectState {
        begin, working, end
    }
}
