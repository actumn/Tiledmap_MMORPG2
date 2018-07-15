package com.mygdx.game.ui.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.map.DrawTile;
import com.mygdx.game.map.Map;

/**
 * Created by Lee on 2016-06-22.
 */
public class MiniMapActor extends BaseActor {

    /* model */
    private Map map;
    private TiledMap tiledMap;

    /* view */
    private Pixmap pixmap;
    private Texture pixmapTexture;
    private TextureRegion minimapRegion;

    /* tile properties */
    private int tileWidth;
    private int tileHeight;

    public MiniMapActor() {
        this(null);
    }

    public MiniMapActor(Map map) {
        final float x = Gdx.graphics.getWidth() - 200f, y = Gdx.graphics.getHeight() - 150f;
        final float width = 100.0f, height = 100.0f;
        this.setPosition(x, y);
        this.setSize(width, height);

        this.map = map;

        this.tiledMap = map.getTiledMap();
        int pixmapWidth = (Integer) tiledMap.getProperties().get("width");
        int pixmapHeight = (Integer) tiledMap.getProperties().get("height");

        this.pixmap = new Pixmap(pixmapWidth+20, pixmapHeight+20, Format.RGBA8888);
        this.pixmap.setColor(Color.BLACK);
        this.pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
        this.pixmapTexture = new Texture(this.pixmap, Format.RGB888, false);
    }

    public MiniMapActor setMap(Map map) {
        this.map = map;
        return this;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        updatePixmap();

        batch.setColor(1.0f, 1.0f, 1.0f, 0.5f);
        batch.draw(pixmapTexture, getX(), getY());
    }

    private void updatePixmap() {


        this.tileWidth = (Integer) this.tiledMap.getProperties().get("tilewidth");
        this.tileHeight = (Integer) this.tiledMap.getProperties().get("tileheight");


        int[] tileColors = new int[3];
        tileColors[0] = Color.rgba8888(0.5f, 0.5f, 0.5f, 1.0f);
        tileColors[1] = Color.rgba8888(0.7f, 0.7f, 1.0f, 1.0f);
        tileColors[2] = Color.rgba8888(1.0f, 1.0f, 0.7f, 1.0f);


        for (MapLayer layer: this.tiledMap.getLayers()) {
            if (layer instanceof TiledMapTileLayer) {
                TiledMapTileLayer tileLayer = (TiledMapTileLayer) layer;
                int count = Integer.parseInt((String) tileLayer.getProperties().get("type"));

                for (int tileY = 0; tileY < tileLayer.getHeight(); tileY += 1) {
                    for (int tileX = 0; tileX < tileLayer.getWidth(); tileX += 1) {
                        if (tileLayer.getCell(tileX, tileY) == null) continue;

                        this.pixmap.drawPixel(tileX, tileLayer.getHeight() - tileY - 1, tileColors[count]);
                    }
                }
            }
        }

        this.pixmapTexture.load(new PixmapTextureData(pixmap, Format.RGBA8888, false, false));
    }
}
