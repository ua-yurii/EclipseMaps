package com.eclipsemaps;

import com.eclipsemaps.service.export.IEclipseMapsService;

public class MapsServiceBinder {
	private static IEclipseMapsService service;

	public synchronized void setService(IEclipseMapsService service) {
		MapsServiceBinder.service = service;
	}

	public synchronized void unsetService(IEclipseMapsService service) {
		if (MapsServiceBinder.service == service) {
			MapsServiceBinder.service = null;
		}
	}

	public static IEclipseMapsService getService() {
		return service;
	}

}
