package com.apgb.theline;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

public class LineThread extends Thread{
	private static final long FPS = 70;
	
	public Random random = new Random();
	public LineLogic logic;
	private boolean running = false;
	private LineSurfaceView canvas = null;
	private SurfaceHolder surfaceHolder = null;
	public boolean firstTime=true, gameOver=false;
	public Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
	private Path p= new Path();
	private int a,j,i;
	public int size;
	private Coordinate start,buffer;
	public Line line;
	
	// gestione Bonus
	public Bonus bonus;
	public int tempoBonus=5000;
	public int tipoBonus;
    public Timer t=null;
	public ArrayList<Bonus> listaBonus=new ArrayList<Bonus>();
	
	//Measure frames per second.
    long now;
    int framesCount=0;
    int framesCountAvg=0;
    long framesTimer=0;
    Paint fpsPaint=new Paint();
	
    
	long ticksPS = 1000 / FPS;
    long startTime;
    long sleepTime;
    
	Canvas c = null;
	public LineThread(LineSurfaceView canvas)
	{
		super();
		
		this.canvas = canvas;
		this.surfaceHolder = canvas.getHolder();
		line= new Line(canvas.stroke);
	    paint.setColor(Color.BLACK);                    // set the color
	    paint.setStrokeWidth(canvas.stroke);               // set the size
	    paint.setDither(true);                    // set the dither to true
	    paint.setStyle(Paint.Style.STROKE);       // set to STOKE
	    paint.setStrokeJoin(Paint.Join.ROUND);    // set the join to round you want
	    paint.setStrokeCap(Paint.Cap.ROUND);      // set the paint cap to round too
	    paint.setPathEffect(new CornerPathEffect(30) );   // set the path effect when they join.
	    paint.setAntiAlias(true);  
	    fpsPaint.setTextSize(15);
	    fpsPaint.setAntiAlias(true); 

	    
	}

	public void startThread()
	{
		running = true;
		super.start();
		super.setPriority(MAX_PRIORITY);
	}

	public void stopThread()
	{
		running = false;
	}

	public void run()
	{
		
		while (running)
		{
			c = null;
			startTime = System.currentTimeMillis();
			try
			{
				c = surfaceHolder.lockCanvas();
				synchronized (surfaceHolder)
				{
					if (c != null)
					{
						inizializzaArray(c);
						if(!gameOver)
							disegna(c);
						else
							schermataGameOver(c);
					}
				}
				  sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
                         if (sleepTime > 0)

                                sleep(sleepTime);

                         else

                                sleep(1);

                  
			}
			
			catch(InterruptedException ie)
			{ 
			}
			finally 
			{
				// do this in a finally so that if an exception is thrown
				// we don't leave the Surface in an inconsistent state
				if (c != null) 
				{
					surfaceHolder.unlockCanvasAndPost(c);
				}
			}
		}
	}

	
	private void schermataGameOver(Canvas c) {
		this.stopThread();
		t.cancel();
		t=null;
		listaBonus.clear();
	}

	public void disegna(Canvas c){
		paint.setStrokeWidth(canvas.gameStroke);
		c.drawColor(Color.WHITE);
		/* appunti per gio momentanei
		 * canvas.graficaBonus.get(0) invincibilità
		 * canvas.graficaBonus.get(1) allarga linea
		 * canvas.graficaBonus.get(2) punti 500
		 * canvas.graficaBonus.get(3) punti 1000
		 * canvas.graficaBonus.get(4) muori e basta
		 * canvas.graficaBonus.get(5) restringi linea
		 * canvas.graficaBonus.get(6) random
		 */
		
		line=logic.avanzaLinea(line);
		p.moveTo(line.line.get(0).x, line.line.get(0).y);
		for(j=1;j<line.line.size();j++){
			p.lineTo(line.line.get(j).x, line.line.get(j).y);
		}
		c.drawPath(p, paint);
		p.reset();
		
		  now=System.currentTimeMillis();
		     c.drawText(framesCountAvg+" fps", 40, 70, fpsPaint);
		     framesCount++;
		     if(now-framesTimer>1000) {
		             framesTimer=now;
		             framesCountAvg=framesCount;
		             framesCount=0;
		     }
		     
		     disegnaBonus(c);
		     disegnaPunti(c);
	}
	
    public void inizializzaArray(Canvas c){
    	if(firstTime){
			a=c.getWidth()/2;
		    
		//Array lungo quanto lo schermo
		size=(c.getHeight())+1;
		buffer=new Coordinate(c.getWidth()/2, c.getHeight()/2);
		logic=new LineLogic(size, a,buffer);
		line.creaArray(size);
		
		start=new Coordinate(c.getWidth()/2, 0);
		for(j=0;j<size;j++){
			buffer=new Coordinate(start.getX(), start.getY()+j);
			line.line.add(j, buffer);			
			}
		firstTime=false;
		creaBonus();
		
		}
    }
    
    public void creaBonus(){
    	if(t==null)
    		t=new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
			
    			@Override
    			public void run() {
    				tipoBonus = random.nextInt(7);
    				bonus=new Bonus(tipoBonus, canvas.getWidth(),canvas.getHeight(), canvas.graficaBonus.get(0).getWidth(), canvas.graficaBonus.get(0).getHeight());
    				listaBonus.add(bonus);
    				for (i =0; i<listaBonus.size();i++)
    				if ((listaBonus.get(i)!=null)&&(listaBonus.get(i).timernt==0))
    				{
    					listaBonus.remove(i);
    				}
    				if(gameOver){
    					cancel();
    				}
    			}
    		}, 0, tempoBonus);
    }
    
    
    public void disegnaPunti(Canvas c){
    	
    	c.drawText(""+canvas.punteggio, 40, 100, fpsPaint);
    }
    
  public void disegnaBonus(Canvas c)
  {
	  listaBonus=logic.calcolaBonus(listaBonus);
	  for( i=0; i<listaBonus.size();i++)
		c.drawBitmap(canvas.graficaBonus.get(listaBonus.get(i).indiceBonus),listaBonus.get(i).x, listaBonus.get(i).y, null);
  }
}

