package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Lee on 2016-06-06.
 */
public class SystemMessage {
    private static SystemMessage instance;
    public static SystemMessage getInstance() {
        if (instance == null) instance = new SystemMessage();
        return instance;
    }

    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    private String message;
    private long expiryTime = 0;
    private int size;
    private float r;
    private float g;
    private float b;
    private float a;
    public SystemMessage() {
        this.shapeRenderer = new ShapeRenderer();
        this.spriteBatch = new SpriteBatch();
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0.0f);
        this.camera.update();
    }

    public void show(String message, int time, int size, float r, float g, float b, float a) {
        this.message = message;
        this.expiryTime = System.currentTimeMillis() + time;
        this.size = size;
        this.r = r; this.g = g; this.b = b; this.a = a;
    }

    public void render() {
        if(System.currentTimeMillis() >= this.expiryTime) return;

        this.shapeRenderer.setProjectionMatrix(this.camera.combined);
        this.spriteBatch.setProjectionMatrix(this.camera.combined);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.shapeRenderer.setColor(0.0f, 0.0f, 0.0f, 0.8f);
        this.shapeRenderer.rect(0, Gdx.graphics.getHeight() / 2 - 20, Gdx.graphics.getWidth(), 40);
        this.shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        this.spriteBatch.begin();
        this.spriteBatch.setColor(r, g, b, a);
        BitmapFont font = Font.getInstance().getFont(size);
        font.setColor(this.r, this.g, this.b, this.a);
        font.draw(this.spriteBatch, this.message,
                Gdx.graphics.getWidth() / 2 - Gdx.graphics.getHeight() * size / Gdx.graphics.getWidth() * this.message.length() / 2,
                Gdx.graphics.getHeight() / 2 + font.getCapHeight() / 2);
        this.spriteBatch.end();
    }
}
