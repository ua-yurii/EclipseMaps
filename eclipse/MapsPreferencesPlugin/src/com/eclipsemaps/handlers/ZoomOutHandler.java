package com.eclipsemaps.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

import com.eclipsemaps.parts.MapViewPart;

public class ZoomOutHandler {
	@Execute
	public static void execute(MPart part) {
		//TODO maybe add check for null and instance off
		MapViewPart mapView = (MapViewPart) part.getObject();
		mapView.zoomOut();
	}

}
