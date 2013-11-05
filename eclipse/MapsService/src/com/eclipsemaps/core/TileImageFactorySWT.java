package com.eclipsemaps.core;

import java.io.InputStream;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

import com.mapslibrary.core.TileImage;
import com.mapslibrary.core.TileImageFactory;

public class TileImageFactorySWT implements TileImageFactory {
	private Device device;
	private Image image;

	public TileImageFactorySWT(Device device) {
		this.device = device;
		this.image = new Image(device, new Rectangle(0, 0, 256, 256));
	}

	@Override
	public TileImage getTileImage(InputStream stream) {
		return TileImageSWT.fromInputStream(device, stream);
	}

	@Override
	public TileImage getTileImage(int type) {
		return TileImageSWT.fromImage(image, type);
	}
}
