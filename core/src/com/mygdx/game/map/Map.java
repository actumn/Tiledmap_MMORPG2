package com.mygdx.game.map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.manager.DrawManager;
import com.mygdx.game.manager.ObjectManager;
import com.mygdx.game.object.Character;
import com.mygdx.game.object.Effect;
import com.mygdx.game.object.Entity;

/**
 * Created by Lee on 2016-05-20.
 */
public class Map {
    private DrawManager drawManager;
    private ObjectManager objectManager;
    private String mapName;
    private TiledMap tiledMap;
    private Skin skin;

    public Map(String mapName, Skin skin) {
        this.mapName = mapName;
        this.skin = skin;
        this.drawManager = new DrawManager();

        this.tiledMap = new TmxMapLoader().load(mapName);
        int layer_count = 0;
        for (MapLayer layer : tiledMap.getLayers()) {
            if (layer instanceof TiledMapTileLayer) {
                TiledMapTileLayer tileLayer = (TiledMapTileLayer)layer;
                for(int y=0; y<tileLayer.getHeight(); y++) {
                    for(int x=0; x<tileLayer.getWidth(); x++) {
                        if(tileLayer.getCell(x, y) == null) continue;
                        this.drawManager.add(new DrawTile(tileLayer, x, y, layer_count));
                    }
                }
                layer_count += 1;
            }
        }
    }


    public void update() {
        //objectManager.update();
        drawManager.draw();
    }

    public void add(Entity entity){
        //objectManager.add(entity);
        drawManager.add(entity);
    }

    public void add(Effect e) {
        drawManager.add(e);
    }

    public boolean checkCollision(int x, int y) {
        int mapWidth = (Integer) this.tiledMap.getProperties().get("width") * (Integer) this.tiledMap.getProperties().get("tilewidth");
        int mapHeight = (Integer) this.tiledMap.getProperties().get("height") * (Integer) this.tiledMap.getProperties().get("tileheight");

        if(x < 0 || y < 0 ||
                x >= mapWidth ||
                y >= mapHeight) return true;

        for(MapLayer layer : this.tiledMap.getLayers()) {
            if (layer instanceof TiledMapTileLayer) {
                TiledMapTileLayer tileLayer = (TiledMapTileLayer)layer;

                int tileX = x/(int)tileLayer.getTileWidth();
                int tileY = y/(int)tileLayer.getTileHeight();

                TiledMapTileLayer.Cell cell = tileLayer.getCell(tileX, tileY);
                if(cell == null) cell = tileLayer.getCell(tileX - 1, tileY);
                if(cell == null) cell = tileLayer.getCell(tileX, tileY - 1);
                if(cell == null) cell = tileLayer.getCell(tileX - 1, tileY - 1);
                if(cell == null) continue;

                if(Integer.parseInt((String)cell.getTile().getProperties().get("passage")) != 0) return true;
            }
        }

        return false;
    }

    public void setCenterCharacter(Character centerCharacter) {
        drawManager.setCenterCharacter(centerCharacter);
    }
}
