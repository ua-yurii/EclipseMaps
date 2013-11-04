package com.maps.core;

public interface TileImage {
	public static final int TYPE_NOIMAGE = -1;
	public static final int TYPE_LOADING = 0;
	public static final int TYPE_LOADED = 1;
	public static final int TYPE_CACHED = 1;

	int getType();

	void setType(int typeCached);

	void dispose();

}
