package com.apgb.theline;

public class Andamento {
	public int x,a;
	
	public Andamento(int ab){
		a=ab;
	}
	
	public int calcolaCos(int Contseno){
		
		 x= (int) ((a/2)*2+((a/1.2)*Math.cos(Math.toRadians(Contseno))));
		return x;
	}
	public int calcolaCos2(int Contseno, double fratto){
		
		 x= (int) ((a/2)*2+((a/fratto)*Math.cos(Math.toRadians(Contseno))));
		
		return x;
	}
}
