package com.eclipsemaps.service;

import com.eclipsemaps.service.export.IEclipseMapsProvider;
import com.maps.core.HttpTilesProvider;
import com.maps.core.MBTilesProvider;
import com.maps.core.TilesProvider;

public class TiledMapsProvider implements IEclipseMapsProvider {
	private String url;
	private TilesProvider tilesProvider;

	static TiledMapsProvider byURL(String url) {
		TiledMapsProvider provider = new TiledMapsProvider();
		provider.url = url;
		if (url.length() > 10)
			provider.tilesProvider = new MBTilesProvider(url);
		else
			provider.tilesProvider = new HttpTilesProvider(url);
		return provider;
	}

	public String getUrl() {
		return url;
	}

	public TilesProvider getTilesProvider() {
		return tilesProvider;
	}

	@Override
	public void open() {
		tilesProvider.open();

	}

	@Override
	public void close() {
		tilesProvider.close();
	}
}
