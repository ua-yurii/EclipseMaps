package com.eclipsemaps.parts;

import java.io.IOException;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.eclipsemaps.MapsServiceBinder;
import com.eclipsemaps.export.IMapWidget;
import com.mapslibrary.GeoPoint;

public class MapViewPart {
	IMapWidget mapWidget;

	@Inject
	public MapViewPart() {
		// TODO Your code here
	}

	@PostConstruct
	public void postConstruct(Composite parent) {
		parent.setLayout(new FillLayout());
		mapWidget = MapsServiceBinder.getService().createWidget(parent);
		mapWidget.setZoom(3);

		float lon = 30.0f;
		float lat = 50.0f;

		mapWidget.setCenterPosition(new GeoPoint(lon, lat));

		try {
			URL url = null;

			url = FileLocator.toFileURL(Platform.getBundle(
					"MapsUIPlugin").getEntry(
					"/resources/world.mbtiles"));

			MapsServiceBinder.getService().addProvider(url.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		// MapsServiceBinder.getService().addProvider(
		// "http://otile1.mqcdn.com/tiles/1.0.0/sat/");

		// MapsServiceBinder.getService().addProvider(
		// "http://tile.openstreetmap.org/");

		MapsServiceBinder.getService().addProvider(
				"http://otile1.mqcdn.com/tiles/1.0.0/osm/");

	}

	@PreDestroy
	public void preDestroy() {
		// TODO Your code here
	}

	@Focus
	public void onFocus() {
		mapWidget.getComposite().setFocus();
	}

	@Persist
	public void save() {
		// TODO Your code here
	}

}