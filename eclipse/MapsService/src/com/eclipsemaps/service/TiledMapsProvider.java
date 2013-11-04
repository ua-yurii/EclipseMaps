package com.eclipsemaps.service;

import java.net.MalformedURLException;
import java.net.URL;

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

		URL tmp = null;

		try {
			tmp = new URL(url);
		} catch (MalformedURLException e) {
			return null;
		}

		if (tmp.getProtocol().equals("http")) {
			provider.tilesProvider = new HttpTilesProvider(url);
			return provider;
		}

		if (tmp.getProtocol().equals("file")) {
			provider.tilesProvider = new MBTilesProvider(tmp.getPath());
			return provider;
		}
		provider = null;
		return null;
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
