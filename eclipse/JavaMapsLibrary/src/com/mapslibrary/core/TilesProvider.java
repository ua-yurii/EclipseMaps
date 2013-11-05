package com.mapslibrary.core;

public interface TilesProvider {
	public void open();

	public void close();

	public TileImage requestTileImage(final Tile tile,
			final TileImageFactory factory, final TileImageListener listener);
}
