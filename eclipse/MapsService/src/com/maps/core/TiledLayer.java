package com.maps.core;

import org.eclipse.swt.widgets.Canvas;

import com.eclipsemaps.export.GeoPoint;

public abstract class TiledLayer {
	protected static final int TILE_SIZE = 256;

	private TilesProvider tilesProvider;

	private GeoPoint center;
	private int zoom;
	private int width, height;
	private Canvas canvas;
	
	protected TiledLayer(Canvas canvas){
		this.canvas = canvas;
	}

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

	public Canvas getCanvas() {
		return canvas;
	}
}
