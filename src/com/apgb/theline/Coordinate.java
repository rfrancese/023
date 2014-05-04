package com.apgb.theline;

public class Coordinate {
	public float y;
	public int x;
	
	public Coordinate(int nx, float ny){
		x=nx;
		y=ny;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
}
