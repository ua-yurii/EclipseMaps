package com.maps.swt;

import java.io.InputStream;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

import com.maps.core.TileImage;

public class TileImageSWT implements TileImage {
	private Image image;
	private int type;

	public static TileImageSWT fromInputStream(Device device, InputStream stream) {
		TileImageSWT ti = new TileImageSWT();
		ti.image = new Image(device, stream);
		ti.type = TYPE_LOADED;
		return ti;
	}

	public static TileImageSWT fromImage(Image image, int type) {
		TileImageSWT ti = new TileImageSWT();
		ti.image = image;
		ti.type = type;
		return ti;
	}

	public void draw(GC gc, int x, int y) {
		gc.drawImage(image, x, y);
	}

	public Image getImage() {
		return image;
	}

	@Override
	public int getType() {
		return type;
	}

	@Override
	public void setType(int type) {
		this.type = type;
	}

	@Override
	public void dispose() {
		image.dispose();
	}

}
