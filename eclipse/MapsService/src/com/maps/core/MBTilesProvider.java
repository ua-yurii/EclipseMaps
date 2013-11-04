package com.maps.core;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.eclipsemaps.export.Bounds;

public class MBTilesProvider extends AbstactCachingTilesProvider {
	private static final int CACHE_SIZE = 16;
	
	private Connection connection = null;
	private String fileName = null;
	private Bounds bounds = null;

	public MBTilesProvider(String fileName) {
		super(CACHE_SIZE);
		this.fileName = fileName;
	}

	@Override
	public void open() {
		super.open();
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			String c = "jdbc:sqlite:" + fileName;
			connection = DriverManager.getConnection(c);
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		readMetadata();

	}

	@Override
	public void close() {
		super.close();
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void readMetadata() {
		if (connection == null)
			return;

		Statement stmt = null;

		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT value FROM metadata  WHERE name='bounds'");

			String tmp = rs.getString("value");
			String[] v = tmp.split(",");
			Bounds bounds = new Bounds();
			bounds.setLontitude1(Float.parseFloat(v[0]));
			bounds.setLantitude1(Float.parseFloat(v[1]));
			bounds.setLontitude2(Float.parseFloat(v[2]));
			bounds.setLantitude2(Float.parseFloat(v[3]));
			this.bounds = bounds;
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private byte[] getTile(int zoom, int x, int y) {
		byte[] result = null;
		Statement stmt = null;

		if (connection == null)
			return null;

		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT tile_data\n"
					+ "FROM tiles\n" + "WHERE zoom_level=" + zoom
					+ "\nAND tile_column=" + x + "\nAND tile_row=" + y + ";");

			if (rs.next()) {
				result = rs.getBytes("tile_data");
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public Bounds getBounds() {
		return bounds;
	}

	@Override
	public TileImage requestTileImage(Tile tile, TileImageFactory factory,
			TileImageListener listener) {

		assert factory == null;

		TileImage img = get(tile);
		if (img == null) {
			byte[] data = getTile(tile.zoom, tile.x, tile.y);
			if (data != null) {
				ByteArrayInputStream bis = new ByteArrayInputStream(data);
				img = factory.getTileImage(bis);
				put(tile, img);
				return img;
			} else {
				img = factory.getTileImage(TileImage.TYPE_NOIMAGE);
				put(tile, img);
				return img;
			}
		}

		return img;
	}
}
