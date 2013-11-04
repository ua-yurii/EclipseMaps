package com.maps.core;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstactCachingTilesProvider implements TilesProvider {
	private HashMap<Tile, TileImage> map;

	protected AbstactCachingTilesProvider(final int cacheSize) {
		map = new LinkedHashMap<Tile, TileImage>(cacheSize + 1, 1f, false) {
			private static final long serialVersionUID = 1L;

			protected boolean removeEldestEntry(
					Map.Entry<Tile, TileImage> eldest) {
				return size() > cacheSize;
			}
		};
	}

	protected void put(Tile tile, TileImage tileImage) {
		map.put(tile, tileImage);
	}

	protected TileImage get(Tile tile) {
		return map.get(tile);
	}

	@Override
	public void open() {

	}

	@Override
	public void close() {
		for(TileImage image : map.values())
			image.dispose();
	}

	@Override
	public abstract TileImage requestTileImage(Tile tile,
			TileImageFactory factory, TileImageListener listener);
}
