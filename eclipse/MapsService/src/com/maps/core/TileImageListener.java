package com.maps.core;

public interface TileImageListener {
	public void tileImageReady(Tile tile, TileImage image);
	
	public void allTileImagesReady();
}
