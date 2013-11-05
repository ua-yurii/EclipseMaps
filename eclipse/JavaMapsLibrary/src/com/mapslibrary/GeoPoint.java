package com.mapslibrary;

public class GeoPoint {
	private float lontitude;
	private float lantitude;
	
	public GeoPoint(float lontitude, float lantitude) {
		super();
		this.lontitude = lontitude;
		this.lantitude = lantitude;
	}
	public float getLontitude() {
		return lontitude;
	}
	public void setLontitude(float lontitude) {
		this.lontitude = lontitude;
	}
	public float getLantitude() {
		return lantitude;
	}
	public void setLantitude(float lantitude) {
		this.lantitude = lantitude;
	}	
}
