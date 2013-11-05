package com.mapslibrary.core;

import com.mapslibrary.GeoPoint;

public abstract class TiledLayer {
	protected static final int TILE_SIZE = 256;

	private TilesProvider tilesProvider;

	private GeoPoint center;
	private int zoom;
	private int width, height;

	public TilesProvider getTilesProvider() {
		return tilesProvider;
	}

	public void setTilesProvider(TilesProvider tilesProvider) {
		if (this.tilesProvider != null) {
			this.tilesProvider.close();
			this.tilesProvider = null;
		}

		this.tilesProvider = tilesProvider;
		tilesProvider.open();
	}

	public void setCenter(GeoPoint center) {
		this.center = center;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	protected int getWidth() {
		return width;
	}

	protected int getHeight() {
		return height;
	}

	protected GeoPoint getCenter() {
		return center;
	}

	protected int getZoom() {
		return zoom;
	}
}
