package com.mapslibrary;

public class Bounds {
	private float lontitude1, lontitude2;
	private float lantitude1, lantitude2;
	
	public Bounds(float lontitude1, float lontitude2, float lantitude1,
			float lantitude2) {
		this.lontitude1 = lontitude1;
		this.lontitude2 = lontitude2;
		this.lantitude1 = lantitude1;
		this.lantitude2 = lantitude2;
	}

	public Bounds() {
		// TODO Auto-generated constructor stub
	}

	public float getLontitude1() {
		return lontitude1;
	}

	public void setLontitude1(float lontitude1) {
		this.lontitude1 = lontitude1;
	}

	public float getLontitude2() {
		return lontitude2;
	}

	public void setLontitude2(float lontitude2) {
		this.lontitude2 = lontitude2;
	}

	public float getLantitude1() {
		return lantitude1;
	}

	public void setLantitude1(float lantitude1) {
		this.lantitude1 = lantitude1;
	}

	public float getLantitude2() {
		return lantitude2;
	}

	public void setLantitude2(float lantitude2) {
		this.lantitude2 = lantitude2;
	}
	
	
}
