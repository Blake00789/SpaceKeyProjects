package com.dicycat.kroy.gamemap;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * @author Martha Cartwright
 *
 */

public class TiledGameMap{
	
	TiledMap tiledMap;
	OrthogonalTiledMapRenderer tiledMapRenderer;
	
	public TiledGameMap() {
		tiledMap = new TmxMapLoader().load("YorkMap.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
	}


	/**
	 * Renders the road layer of the map
	 * @param camera
	 */
	public void renderRoads(OrthographicCamera camera) {
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render(new int[] {1,2});
	}


	/**
	 * Renders the building layer and the window layer of the map
	 * @param camera
	 */
	public void renderBuildings(OrthographicCamera camera) {
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render(new int[] {3,4});
	}

	public void update(float delta) {}


	public void dispose() {
		tiledMap.dispose();
	}


	/**
	 * Gets the tile type at a row/column for a particular layer
	 * @param layer
	 * @param col
	 * @param row
	 * @return tileType
	 */
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
	

	/**
	 * Gets the tile type at a pixel position for a particular layer
	 * @param layer
	 * @param x
	 * @param y
	 * @return tileType
	 */
	public TileType getTileTypeByLocation(int layer, float x, float y) {
		return this.getTileTypeByCoordinate(layer, (int)(x/TileType.TILE_SIZE), (int)(y/TileType.TILE_SIZE));
	}


	/**
	 * Gets the width of the map in tiles
	 * @return width
	 */
	public int getWidth() {
		return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getWidth();
	}


	/**
	 * Gets the height of the map in tiles
	 * @return height
	 */
	public int getHeight() {
		return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getHeight();
	}


	public int getLayers() {
		return tiledMap.getLayers().getCount();
	}
	
	
	/**
	 * Gets the width of the map in pixels
	 * @return width
	 */
	public int getPixelWidth() {
		return this.getWidth() * TileType.TILE_SIZE;
	}
	
	/**
	 * Gets the height of the map in pixels
	 * @return height
	 */
	public int getPixelHeight() {
		return this.getHeight() * TileType.TILE_SIZE;
	}
}
