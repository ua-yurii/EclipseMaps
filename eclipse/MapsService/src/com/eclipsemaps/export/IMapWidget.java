package com.eclipsemaps.export;

import org.eclipse.swt.widgets.Canvas;


public interface IMapWidget {

	public void setZoom(int zoom);

	public void setCenterPosition(GeoPoint center);
	
	public Canvas getComposite();

}