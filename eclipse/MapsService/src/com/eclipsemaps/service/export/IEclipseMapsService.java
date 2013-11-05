package com.eclipsemaps.service.export;

import java.util.List;

import org.eclipse.swt.widgets.Composite;

import com.eclipsemaps.export.IMapWidget;

public interface IEclipseMapsService {
	public List<IEclipseMapsProvider> getAllProviders();

	public void addProvider(String url);

	public void removeProvider(String url);

	public void registerListener(IEclipseMapsServiceListener listener);

	public void deregisterListener(IEclipseMapsServiceListener listener);

	public IMapWidget createWidget(Composite parent);
}
