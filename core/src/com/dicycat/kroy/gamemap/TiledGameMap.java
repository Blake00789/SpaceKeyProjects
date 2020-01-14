package com.dicycat.kroy.gamemap;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

public class TiledGameMap{
	
	TiledMap tiledMap;
	OrthogonalTiledMapRenderer tiledMapRenderer;
	
	public TiledGameMap() {
		tiledMap = new TmxMapLoader().load("YorkMap.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
	}


	public void renderRoads(OrthographicCamera camera) {
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render(new int[] {1,2});
	}
	
	public void renderBuildings(OrthographicCamera camera) {
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render(new int[] {3,4});
	}




	public void update(float delta) {
		// TODO Auto-generated method stub

	}


	public void dispose() {
		tiledMap.dispose();
	}


	public TileType getTileTypeByCoordinate(int layer, int col, int row) {
		Cell cell = ((TiledMapTileLayer) tiledMap.getLayers().get(layer)).getCell(col,row);
		if (cell != null) {
			TiledMapTile tile = cell.getTile();
			
			if (tile != null) {
				int id = tile.getId();
				return TileType.getTileTypeByID(id);
			}
		}
		return null;
	}
	
	public TileType getTileTypeByLocation(int layer, float x, float y) {
		return this.getTileTypeByCoordinate(layer, (int)(x/TileType.TILE_SIZE), (int)(y/TileType.TILE_SIZE));
	}


	public int getWidth() {
		return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getWidth();
	}


	public int getHeight() {
		return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getHeight();
	}


	public int getLayers() {
		return tiledMap.getLayers().getCount();
	}
	
	public int getPixelWidth() {
		return this.getWidth() * TileType.TILE_SIZE;
	}
	
	public int getPixelHeight() {
		return this.getHeight() * TileType.TILE_SIZE;
	}
	
	public boolean isOnRoad(Vector2 pos, int entityWidth, int entityHeight) {
		if(this.getTileTypeByLocation(0, pos.x, pos.y).isCollidable()
				||this.getTileTypeByLocation(0, pos.x + entityWidth, pos.y).isCollidable()
				||this.getTileTypeByLocation(0, pos.x, pos.y+entityHeight).isCollidable()
				||this.getTileTypeByLocation(0, pos.x+entityWidth, pos.y+entityHeight).isCollidable()) {
			return false;
		}
		return true;
	}

}
