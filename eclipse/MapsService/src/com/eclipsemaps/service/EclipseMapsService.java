package com.eclipsemaps.service;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.prefs.Preferences;

import com.eclipsemaps.export.IMapWidget;
import com.eclipsemaps.service.export.IEclipseMapsProvider;
import com.eclipsemaps.service.export.IEclipseMapsService;
import com.eclipsemaps.service.export.IEclipseMapsServiceListener;
import com.maps.core.Tile;
import com.maps.core.TileImage;
import com.maps.core.TileImageFactory;
import com.maps.core.TileImageListener;
import com.maps.core.TilesProvider;
import com.maps.swt.MapWidget;

public class EclipseMapsService implements IEclipseMapsService {
	private LinkedHashMap<String, IEclipseMapsProvider> providers = new LinkedHashMap<>();
	private List<IEclipseMapsServiceListener> listeners = new LinkedList<>();
	private EclipseMapsProviderFactory factory = new EclipseMapsProviderFactory();

	public EclipseMapsService() {
	
	}

	private void loadPreferences() {
		Preferences preferences = InstanceScope.INSTANCE
				.getNode("com.eclipsemaps.service");

		String urls[] = preferences.get("mapsResources", "").split(";");
		for (String url : urls) {
			addProvider(url);
		}
	}

	private void savePreferences() {
		String tmp = "";
		for (IEclipseMapsProvider provider : providers.values()) {
			tmp += provider.getUrl() + ";";
		}

		Preferences preferences = InstanceScope.INSTANCE
				.getNode("com.eclipsemaps.service");

		preferences.put("mapsResources", tmp);
	}

	@Override
	public List<IEclipseMapsProvider> getAllProviders() {
		return new LinkedList<IEclipseMapsProvider>(providers.values());
	}

	@Override
	public void addProvider(String url) {
		if (providers.containsKey(url))
			return;

		IEclipseMapsProvider provider = factory.getEclipseMapsProvider(url);
		if (provider != null) {
			providers.put(url, provider);
			provider.open();
			for (IEclipseMapsServiceListener listener : listeners)
				listener.providerAdded(provider);
		}
	}

	@Override
	public void removeProvider(String url) {
		IEclipseMapsProvider provider = providers.remove(url);

		if (provider != null)
			for (IEclipseMapsServiceListener listener : listeners)
				listener.providerRemoved(provider);

	}

	@Override
	public void registerListener(IEclipseMapsServiceListener listener) {
		listeners.add(listener);
	}

	@Override
	public void deregisterListener(IEclipseMapsServiceListener listener) {
		listeners.remove(listener);
	}

	protected final void activate(ComponentContext cContext) {
		loadPreferences();
	}

	protected final void deactivate(ComponentContext cContext) {
		savePreferences();
		for (IEclipseMapsProvider provider : providers.values())
			provider.close();
	}

	@Override
	public IMapWidget createWidget(Composite parent) {
		MapWidget mapWidget = new MapWidget(parent, SWT.NONE);
		mapWidget.setProvider(new TilesProvider() {

			@Override
			public void open() {

			}

			@Override
			public void close() {

			}

			@Override
			public TileImage requestTileImage(Tile tile,
					TileImageFactory factory, TileImageListener listener) {
				TileImage image = null;

				for (IEclipseMapsProvider provider : providers.values()) {
					if (provider instanceof TiledMapsProvider) {
						TilesProvider tp = ((TiledMapsProvider) provider)
								.getTilesProvider();

						image = tp.requestTileImage(tile, factory, listener);

						if (image.getType() != TileImage.TYPE_NOIMAGE)
							return image;
					}
				}
				return factory.getTileImage(TileImage.TYPE_NOIMAGE);
			}
		});
		return mapWidget;
	}

}
