package com.maps.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import com.eclipsemaps.export.GeoPoint;
import com.eclipsemaps.export.IMapWidget;
import com.eclipsemaps.export.Utils;
import com.maps.core.TilesProvider;
import com.maps.swt.TileImageFactorySWT;
import com.maps.swt.TiledLayerSWT;

public class MapWidget extends Canvas implements IMapWidget {
	private TiledLayerSWT drawer;
	private TileImageFactorySWT factory;
	private int zoom;
	private float lon, lat, tmplat;

	public MapWidget(Composite parent, int style) {
		super(parent, SWT.DOUBLE_BUFFERED | style);

		addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				MapWidget.this.paintControl(e);
			}
		});

		addMouseWheelListener(listener);
		addMouseListener(listener);
		addMouseMoveListener(listener);

		drawer = new TiledLayerSWT(this);
		factory = new TileImageFactorySWT(getDisplay());
	}

	protected void paintControl(PaintEvent e) {
		Color background = new Color(e.display, 50, 50, 50);
		e.gc.setBackground(background);
		e.gc.fillRectangle(0, 0, getSize().x, getSize().y);

		drawer.setSize(getSize().x, getSize().y);
		drawer.paint(e.gc, factory);

		background.dispose();
		e.gc.dispose();

	}

	@Override
	public void setZoom(int zoom) {
		this.zoom = zoom;
		drawer.setZoom(zoom);
	}

	@Override
	public void setCenterPosition(GeoPoint center) {
		this.lon = center.getLontitude();
		this.lat = center.getLantitude();
		drawer.setCenter(center);
	}

	public void setProvider(final TilesProvider provider) {
		drawer.setTilesProvider(provider);
	}

	private ControlListener listener = new ControlListener();

	private class ControlListener implements MouseWheelListener, MouseListener,
			MouseMoveListener {
		private int startX, startY;
		private boolean moving = false;

		@Override
		public void mouseScrolled(MouseEvent e) {
			int tmp = zoom;

			if (e.count > 0)
				tmp++;
			else
				tmp--;

			if (tmp < 1)
				tmp = 1;

			if (tmp > 16)
				tmp = 16;

			zoom = tmp;
			drawer.setZoom(zoom);
			redraw();
		}

		@Override
		public void mouseDoubleClick(MouseEvent e) {

		}

		@Override
		public void mouseDown(MouseEvent e) {
			if (e.button == 1) {
				startX = e.x;
				startY = e.y;
				tmplat = lat;
				moving = true;
			}
		}

		@Override
		public void mouseUp(MouseEvent e) {
			if (e.button == 1) {
				mapMoved(e.x - startX, e.y - startY);
			}

			moving = false;
		}

		@Override
		public void mouseMove(MouseEvent e) {
			if (moving) {
				mapMoving(e.x - startX, e.y - startY);
			}
		}

	}

	public void mapMoving(int x, int y) {
		float scaleX = Utils.getScaleX(zoom, 256);
		float scaleY = Utils.getScaleY(zoom, 256, tmplat);

		tmplat = lat + y * scaleY;

		if (tmplat > 84.0f)
			tmplat = 84.0f;

		if (tmplat < -84.0f)
			tmplat = -84.0f;

		drawer.setCenter(new GeoPoint(lon - x * scaleX, tmplat));
		redraw();
	}

	public void mapMoved(int x, int y) {
		float scaleX = Utils.getScaleX(zoom, 256);
		lon -= x * scaleX;
		lat = tmplat;
		drawer.setCenter(new GeoPoint(lon, lat));
		redraw();
	}

	@Override
	public Canvas getComposite() {
		return this;
	}

}
