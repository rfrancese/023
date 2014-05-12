package com.apgb.theline;

import java.util.ArrayList;

public class Line {
	public float larghezzaLinea;
	public ArrayList<Coordinate> line;
	
	public Line(float stroke) {
		this.larghezzaLinea = stroke;
		this.line = null;
	}
	public Line(int larghezzaLinea ) {
		this.larghezzaLinea = larghezzaLinea;
	}
	public float getLarghezzaLinea() {
		return larghezzaLinea;
	}
	public void setLarghezzaLinea(int larghezzaLinea) {
		this.larghezzaLinea = larghezzaLinea;
	}
	public ArrayList<Coordinate> getLine() {
		return line;
	}
	public void setLine(ArrayList<Coordinate> line) {
		this.line = line;
	}
	public void creaArray(int size) {
		line=new ArrayList<Coordinate>(size);
		
	}
	
}
