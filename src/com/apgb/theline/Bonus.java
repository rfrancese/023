package com.apgb.theline;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Bonus {
	public int x,y;
	public int heigth,width;
	public boolean active=false;
	public Random random = new Random();
	public int indiceBonus;
	public Timer tnt=new Timer();
	public Timer ta=new Timer();
	public int timernt;// timer not touch
	public int timera; // timer quando il bonus e attivo
	public Bonus(int i, int widthCanvas, int heigthCanvas, int widthBonus, int heigthBonus)
	{
		timernt=10;
		timera=5;
		 tnt.scheduleAtFixedRate(new TimerTask() {
				
				@Override
				public void run() {
					if(!active)
					if(timernt>0)
					timernt--;
				}
			}, 0, 1000);
		x=random.nextInt(widthCanvas-widthBonus);
		y=0;
		heigth=heigthBonus;
		width=widthBonus;
		indiceBonus=i;
		
		
		
	}
	public int getHeigth() {
		return heigth;
	}
	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getIndiceBonus() {
		return indiceBonus;
	}
	public void setIndiceBonus(int indiceBonus) {
		this.indiceBonus = indiceBonus;
	}
	
	public void attiva(){
		active=true;
		ta.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				if(timera>0)
					timera--;
			}
		}, 0, 1000);
	}

}
