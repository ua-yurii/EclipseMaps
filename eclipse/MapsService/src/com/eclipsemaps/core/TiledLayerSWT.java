package com.eclipsemaps.core;


import java.awt.Point;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;

import com.mapslibrary.Utils;
import com.mapslibrary.core.Tile;
import com.mapslibrary.core.TileImage;
import com.mapslibrary.core.TileImageFactory;
import com.mapslibrary.core.TileImageListener;
import com.mapslibrary.core.TiledLayer;

public class TiledLayerSWT extends TiledLayer {
	private Canvas canvas;

	protected TiledLayerSWT(Canvas canvas) {
		this.canvas = canvas;
	}

	private Runnable updater = new Runnable() {
		@Override
		public void run() {
			canvas.redraw();
		}
	};

	private TileImageListener listener = new TileImageListener() {

		@Override
		public void tileImageReady(Tile tile, TileImage image) {
			canvas.getDisplay().syncExec(updater);
		}

		@Override
		public void allTileImagesReady() {
			//getCanvas().getDisplay().syncExec(updater);
		}
	};

	public void paint(GC gc, TileImageFactory factory) {
		Point offset = Utils.getTileOffset(getZoom(), getCenter(), TILE_SIZE);

		int centerX = getWidth() / 2 - offset.x;
		int centerY = getHeight() / 2 + offset.y - TILE_SIZE;

		if (getTilesProvider() == null)
			return;

		Tile center = Tile.byGeoPoint(getZoom(), getCenter());

		int top = centerY / TILE_SIZE + 1;
		int bottom = (getHeight() - centerY) / TILE_SIZE + 1;
		int left = centerX / TILE_SIZE + 1;
		int right = (getWidth() - centerX) / TILE_SIZE + 1;

		for (int i = -top; i < bottom; i++)
			for (int j = -left; j < right; j++) {
				Tile tile = new Tile(center, j, -i);
				TileImage tmp;
				try {
					tmp = getTilesProvider().requestTileImage(tile, factory,
							listener);

					if (tmp != null) {
						TileImageSWT image = (TileImageSWT) tmp;
						image.draw(gc, centerX + j * TILE_SIZE, centerY + i
								* TILE_SIZE);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	}
}
