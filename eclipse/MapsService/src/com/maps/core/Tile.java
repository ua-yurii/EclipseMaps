package com.maps.core;

import com.eclipsemaps.export.GeoPoint;
import com.eclipsemaps.export.Utils;

public class Tile {
	public final int zoom;
	public final int x;
	public final int y;


	public static Tile byGeoPoint(int zoom, GeoPoint point) {
		double x = (point.getLontitude() / 360.0 + 0.5) * (1 << zoom);
		double y = (Utils.lat2y(point.getLantitude()) / 360.0 + 0.5)
				* (1 << zoom);

		int tile_x = (int) Math.floor(x);
		int tile_y = (int) Math.floor(y);

		return new Tile(zoom, tile_x, tile_y);
	}
	


	public Tile(int zoom, int x, int y) {
		this.x = x;
		this.y = y;
		this.zoom = zoom;
	}

	public Tile(Tile tile, int dx, int dy) {
		this.x = tile.x + dx;
		this.y = tile.y + dy;
		this.zoom = tile.zoom;
	}

	@Override
	public int hashCode() {
		return (zoom << 21) ^ (x << 9) ^ y;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;

		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (zoom != other.zoom)
			return false;
		return true;
	}
}
