package com.eclipsemaps.service;

import com.eclipsemaps.service.export.IEclipseMapsProvider;

public class EclipseMapsProviderFactory {
	public IEclipseMapsProvider getEclipseMapsProvider(String url) {
		if(url.equals(""))
			return null;
		
		return TiledMapsProvider.byURL(url);
	}
}
