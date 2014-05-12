package com.apgb.theline;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Vibrator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;



public class LineSurfaceView extends SurfaceView implements SurfaceHolder.Callback
{
	
	public class TouchThread extends Thread{
		@Override
        public void run() {
        	while(running){
        		
        	if(down){
        		prendiBonus(evT);
        		prendiPunti(evT);

        	}
        	
        	
        	try {
				sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
        	}
        }
	}

	public class GameOverThread extends Thread{
		@Override
        public void run() {
        	while(running){
        	if(onLine){
        		if(gameStroke<stroke){
        			gameStroke=(float) (gameStroke+0.2);
        			//thread.line.larghezzaLinea++;
        		}
        	}
        	else if(!invincibile )
        	{
        		if(gameStroke<0){
        			//gameOver
        			thread.gameOver=true;
        			context=getContext();
        			Intent intent = new Intent(context, GameOver.class);
        			intent.putExtra("score", ""+punteggio);
        			context.startActivity(intent);
        		}
        		else{
        			gameStroke=(float) (gameStroke-0.4);
        			//thread.line.larghezzaLinea--;
        		}
        	}
        	
        	
        	try {
				sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
        	}
        }
	}
	
	private Context context;
	private LineThread thread = null;
	public ArrayList<Coordinate> lineStat;
	public ArrayList<Bonus> bonusStat;
	private int x,y,i,j;
	public float stroke,gameStroke;
	public int punteggio=0;
	private boolean started, down=false, onLine=false , invincibile=false, lineaDoppia=false;
	private boolean running=true;
	private MotionEvent evT;
	public ArrayList<Bitmap> graficaBonus;
	public Bonus bufferBonus;
	private Random random= new Random();
	private Thread myThread = null;
	private Thread gameOverThread= null;
	private Vibrator v;

	public LineSurfaceView(Context context)
	{
		super(context);
		this.context = context;

		getHolder().addCallback(this);
	}



	public void startGame(float stroke2, ArrayList<Bitmap> b)
	{
		stroke=stroke2;
		gameStroke=stroke2;
		graficaBonus=b;
		onLine=false;
		if (thread == null)
		{
			thread = new LineThread(this);
			thread.startThread();
			thread.setPriority(Thread.MAX_PRIORITY);
		}
		if(gameOverThread== null){
			gameOverThread=new GameOverThread();
			gameOverThread.start();
		}
	}

	public void stopGame()
	{
		started=false;
		running=false;
		if (thread != null)
		{
			thread.stopThread();

			// Waiting for the thread to die by calling thread.join,
			// repeatedly if necessary
			boolean retry = true;
			while (retry)
			{
				try
				{
					if(myThread!=null)
					myThread.join();
					gameOverThread.join();
					thread.join();
					retry = false;
				} 
				catch (InterruptedException e)
				{
				}
			}
			thread = null;
			myThread=null;
			gameOverThread= null;
		}
	}




	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{
	}

	public void surfaceCreated(SurfaceHolder holder)
	{
		startGame(stroke, graficaBonus);
	}

	public void surfaceDestroyed(SurfaceHolder holder)
	{
		stopGame();
	}
	
	 @Override
	 public synchronized boolean onTouchEvent(MotionEvent ev) {
		 if(thread !=null && thread.line !=null){
		 lineStat=thread.line.line;
		 bonusStat=thread.listaBonus;
		 switch(ev.getAction()) {
	        case (MotionEvent.ACTION_DOWN) :
	        	down=true;
	        	prendiBonus(ev);
	        	prendiPunti(ev);
	            	if(myThread== null){
	            		myThread=new TouchThread();
	            		myThread.setPriority(Thread.MAX_PRIORITY);
	            		myThread.start();
	            	evT=MotionEvent.obtain(ev);
	            	running=true;
	            	}
	            	else{
	            		down=true;
	            		evT=MotionEvent.obtain(ev);
	            	}
	            break;
	            
	            
	            
	        case (MotionEvent.ACTION_MOVE) :
	        down=false;
	        prendiBonus(ev);
	        prendiPunti(ev);
	        evT=MotionEvent.obtain(ev);
	        down=true;
	        break;
	            
	            
	            
	            
	        case (MotionEvent.ACTION_UP) :
	        	down=false;
	        	onLine=false;
	        	 break;
	        case (MotionEvent.ACTION_CANCEL) :
	        	down=false;
	        	onLine=false;
	        	 break;
	       
	    }
		 }
		return true;      
		 
	    }
	 
	 private void prendiPunti(MotionEvent ev){
		 for(i=0;i<thread.size-1;i++){
     		
 			if(ev.getX()>(int)lineStat.get(i).getX()-((gameStroke/2)+20) && ev.getX()<(int)lineStat.get(i).getX()+((gameStroke/2)+20)){
 				if(ev.getY()>(int)lineStat.get(i).getY()-(gameStroke-5) && ev.getY()<(int)lineStat.get(i).getY()+(gameStroke-5)){
 					punteggio++;
 					onLine=true;
 					break;
 				} else
 					onLine=false;
 			}
     	
     }
	 }
	 
	 private void prendiBonus(MotionEvent ev){
		 try{
		 for(j=0;j<thread.listaBonus.size();j++){
			 if(ev.getRawX()>(int)thread.listaBonus.get(j).getX() && ev.getRawX()<(int)thread.listaBonus.get(j).getX()+(thread.listaBonus.get(j).width)){
	 				if(ev.getRawY()>(int)thread.listaBonus.get(j).getY() && ev.getRawY()<(int)thread.listaBonus.get(j).getY()+(thread.listaBonus.get(j).heigth)){
	 					//Log.d("touch", "toccato");
	 					attivaBonus(thread.listaBonus.remove(j), j);
	 					v = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
	 					 v.vibrate(250);
	 					 break;
	 				}
	 			}
		 }
		 } catch(IndexOutOfBoundsException e){
			// Log.d("touch", "Errore catch");
		 }
		 
	 }
	 
	 private void attivaBonus(Bonus b, int j){
			switch(b.indiceBonus){
				case 0:
					attivaInv();
					break;
				case 1:
					attivaLineaDoppia();
					break;
				case 2:
					punteggio=punteggio+500;
					break;
				case 3:
					punteggio=punteggio+1000;
					break;
				case 4:
					if(!invincibile)
					gameStroke=0;
					break;
				case 5:
					if(!invincibile)
					gameStroke=gameStroke-gameStroke/3;
					break;
				case 6:
					attivaBonusRandom(random.nextInt(6));
					break;
				}
	 }
	 
	 private void attivaBonusRandom(int indice){
		 switch(indice){
		 	case 0:
			 	attivaInv();
				break;
			case 1:
				attivaLineaDoppia();
				break;
			case 2:
				punteggio=punteggio+500;
				break;
			case 3:
				punteggio=punteggio+1000;
				break;
			case 4:
				if(!invincibile)
				gameStroke=0;
				break;
			case 5:
				if(!invincibile)
				gameStroke=gameStroke-gameStroke/3;
				break;
			}
	 }
	 
	 public void attivaInv(){
		 if(!invincibile){
				invincibile=true;
				thread.paint.setColor(Color.YELLOW);
				Timer ta=new Timer();
				ta.scheduleAtFixedRate(new TimerTask() {
					int timerInv=10;
					@Override
					public void run() {
						if(timerInv==0){
							invincibile=false;
							if(!thread.gameOver)
							thread.paint.setColor(Color.BLACK);
							cancel();
						}
						timerInv--;
					}
				}, 0, 1000);
				}
	 }
	 
	 public void attivaLineaDoppia(){
		 if(!lineaDoppia){
				lineaDoppia=true;
				gameStroke=gameStroke+gameStroke/3;
				Timer tl=new Timer();
				tl.scheduleAtFixedRate(new TimerTask() {
					int timerL=10;
					@Override
					public void run() {
						if(timerL==0){
							lineaDoppia=false;
							if(gameStroke>stroke)
							gameStroke=stroke;
							cancel();
						}
						timerL--;
						}
				}, 0, 1000);
				}
	 }
}
