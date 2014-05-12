package com.apgb.theline;

import java.util.ArrayList;
import java.util.Random;

import android.util.Log;

public class LineLogic {
	
	// Variabili funzionamento
	public float yVecchioPunto;
	private int x,j;
	private Coordinate newPunto,center;
	private int size;
	
	// Variabili impostazioni
	public Andamento andamento;
	public int speed=8;
	public int j1=0;
	public boolean randomfratto=true;
	public double fratto=2;
	public double oldfratto;
	public double randomFratto=0;
	public boolean scegliFratto=true;
	public int contseno=0;

	public double randomParametri=0;
	public double passoParametro=0;
	
	public int lambda=2;
	public int extremeLambda=0;
	public boolean sceltalambda=true;
	
	public int timereffetti=0;
	public boolean sfumatura=true;
    Random random=new Random();
	private boolean controllofratto=true;
	private int oldcontseno;

	
	
	public LineLogic(int s, int ab, Coordinate c){
		size=s;
		andamento=new Andamento(ab);
		center=c;
	}
	
	public Line avanzaLinea(Line line){
		
		
		if (randomParametri>=timereffetti)
		{
			if((contseno==45)||(contseno==135)||(contseno==225))
			{
				
				lambda=random.nextInt(2);
			}
		 
		 if((contseno==180)||(contseno==90)||(contseno==0)||(contseno==360)||(contseno==130))
		 {
			 
		  if (contseno==90) scegliFratto=true;
		  lambda=random.nextInt(10);
		  extremeLambda=random.nextInt(100);
		  if(extremeLambda>90) lambda=30;
		  if((extremeLambda>70)&&(extremeLambda<75)) lambda=0;
		  if((extremeLambda>60)&&(extremeLambda<70)) fratto=1;
		  if((extremeLambda>50)&&(extremeLambda<55)) fratto=4;
		  randomParametri=0;
		
		  
		 }
		 
		     
		
		
		
		
		
		
		timereffetti=random.nextInt(2)+1;
		if( scegliFratto==true)
		{
		 oldfratto=random.nextInt(4);
		 if (oldfratto==0) oldfratto=1;
		 scegliFratto=false;
		}
		if(scegliFratto==false)
		{
		if((fratto<1) || (fratto>4)) scegliFratto=true;
		{
		if(fratto<oldfratto) 
			{
			randomFratto=random.nextInt(2);
			randomFratto=randomFratto/100;
			fratto=fratto+randomFratto;
			}
			}
		if(fratto>oldfratto) 
			
			   {
				randomFratto=random.nextInt(2);
				randomFratto=randomFratto/100;
				fratto=fratto-randomFratto;
				}
			}
		}
	
	//
		
		
		
		passoParametro=(random.nextInt(20)+1);
		passoParametro=passoParametro/100;
	
		randomParametri=randomParametri+passoParametro;
		
		

		
		
		x=andamento.calcolaCos2(contseno,fratto);
	
		contseno=(contseno+lambda)%360;
		newPunto= new Coordinate(x, line.line.get(0).getY());
		line.line.add(0, newPunto);
		for(j=1;j<line.line.size();j++){
			yVecchioPunto = line.line.get(j).getY();
			line.line.get(j).setY(yVecchioPunto+speed+j1);
		}
		
		line.line.remove(line.line.size()-1);
		return line;
	}
public ArrayList<Bonus> calcolaBonus(ArrayList<Bonus> listaBonus)
{
  
	for(int i =0; i<listaBonus.size();i++)
	{
		//listaBonus.get(i).x+=5;
		listaBonus.get(i).y+=1;
		
	}
	return listaBonus;
}
	

}
