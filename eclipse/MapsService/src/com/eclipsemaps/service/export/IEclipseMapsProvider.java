package com.eclipsemaps.service.export;


public interface IEclipseMapsProvider {
	public String getUrl();
	
	public void open();
	
	public void close();
}
