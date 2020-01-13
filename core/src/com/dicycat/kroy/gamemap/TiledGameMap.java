package com.dicycat.kroy.gamemap;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TiledGameMap{
	
	TiledMap tiledMap;
	OrthogonalTiledMapRenderer tiledMapRenderer;
	
	public TiledGameMap() {
		tiledMap = new TmxMapLoader().load("YorkMap.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
	}

	// Renders the ground layer of the TiledGameMap
	public void renderGround(OrthographicCamera camera) {
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render(new int[] {1,2});
	}
	
	// Renders the buildings layer and the windows layer of the TiledGameMap
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

	// Returns the TileType at (x,y) on a given layer
	public TileType getTileTypeByLocation(int layer, float x, float y) {
		int col = (int) x/TileType.TILE_SIZE;
		int row = (int) x/TileType.TILE_SIZE;
		Cell cell = ((TiledMapTileLayer) tiledMap.getLayers().get(layer)).getCell(col,row);
		if (cell != null) {
			TiledMapTile tile = cell.getTile();
			if (tile != null) {
				return TileType.getTileTypeByID(tile.getId());
			}
		}
		return null;
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

}
