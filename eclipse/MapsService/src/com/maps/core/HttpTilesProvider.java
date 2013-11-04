package com.maps.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class HttpTilesProvider implements TilesProvider {
	private HashMap<Tile, TileImage> map;
	private String server;

	public HttpTilesProvider(String url) {
		this.server = url;
		map = new HashMap<Tile, TileImage>();
	}

	@Override
	public void open() {

	}

	@Override
	public void close() {

	}

	private InputStream getTile(int zoom, int x, int y) throws IOException {
		InputStream in = null;
		URL url;

		int max = 1 << zoom;

		if ((x >= max) || (y >= max) || (x < 0) || (y < 0))
			return null;

		if (zoom > 0)
			y = max - 1 - y;

		server = "http://tile.openstreetmap.org/";
		server = "http://otile1.mqcdn.com/tiles/1.0.0/sat/";

		url = new URL(server + zoom + "/" + x + "/" + y + ".png");

		URLConnection conn = url.openConnection();
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setConnectTimeout(1000);
		in = conn.getInputStream();

		return in;
	}

	private class AsyncRequest extends Thread {
		private TileImageListener listener;
		private TileImageFactory factory;
		private Tile tile;

		public AsyncRequest(Tile tile, TileImageFactory factory,
				TileImageListener listener) {
			this.tile = tile;
			this.factory = factory;
			this.listener = listener;
		}

		@Override
		public void run() {
			InputStream data = null;
			try {
				data = getTile(tile.zoom, tile.x, tile.y);
			} catch (IOException e) {
				data = null;
				online = false;
			}

			if (data != null) {
				TileImage img = factory.getTileImage(data);
				online = true;

				try {
					data.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				img.setType(TileImage.TYPE_LOADED);
				map.put(tile, img);

				if (listener != null) {
					listener.tileImageReady(tile, img);

					if (requests.isEmpty())
						listener.allTileImagesReady();
				}
			}

			requests.remove(tile);
		}
	}

	private HashMap<Tile, AsyncRequest> requests = new HashMap<Tile, AsyncRequest>();
	private boolean online = true;

	@Override
	public TileImage requestTileImage(Tile tile, TileImageFactory factory,
			TileImageListener listener) {
		assert factory == null;

		TileImage img = map.get(tile);

		if (img == null) {
			if (!requests.containsKey(tile)) {
				AsyncRequest request = new AsyncRequest(tile, factory, listener);
				requests.put(tile, request);
				request.start();
			}

			if (online)
				return factory.getTileImage(TileImage.TYPE_LOADING);
			else
				return factory.getTileImage(TileImage.TYPE_NOIMAGE);
		}

		return img;
	}
}
