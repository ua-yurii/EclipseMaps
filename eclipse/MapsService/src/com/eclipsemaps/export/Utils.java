package com.eclipsemaps.export;

import java.awt.Point;
import java.lang.Math;

public class Utils {
	private static final double PI = Math.PI;

	public static double y2lat(double aY) {
		return Math.toDegrees(2 * Math.atan(Math.exp(Math.toRadians(aY)))
				- Math.PI / 2);
	}

	public static double lat2y(double aLat) {
		return 180.0 / PI * Math.log(Math.tan(PI / 4 + aLat * PI / 360.0));
	}

	public static Point getTileOffset(int zoom, GeoPoint point, float tileSize) {
		double x = (point.getLontitude() / 360.0 + 0.5) * (1 << zoom);
		double y = (Utils.lat2y(point.getLantitude()) / 360.0 + 0.5)
				* (1 << zoom);

		double tile_x = Math.floor(x);
		double tile_y = Math.floor(y);

		return new Point((int) ((x - tile_x) * tileSize),
				(int) ((y - tile_y) * tileSize));
	}

	public static float getScaleX(int zoom, float tileSize) {
		return 360.0f / (1 << zoom) / tileSize;
	}

	public static float getScaleY(int zoom, float tileSize, float latitude) {
		return (float) (360f / (1 << zoom) / tileSize * Math.cos(latitude
				* PI / 180.0));
	}

}
