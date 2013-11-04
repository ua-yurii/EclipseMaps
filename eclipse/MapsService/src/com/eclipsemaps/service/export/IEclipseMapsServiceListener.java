package com.eclipsemaps.service.export;

public interface IEclipseMapsServiceListener {
	public void providerAdded(IEclipseMapsProvider provider);
	public void providerRemoved(IEclipseMapsProvider provider);
	
}
