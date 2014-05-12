package com.apgb.theline;


import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class NewGame extends Activity implements OnTouchListener {
	LineSurfaceView v;
	int stroke;
	ArrayList<Bitmap> bonus;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  v= new LineSurfaceView(this);
		  v.setOnTouchListener(this);
		  stroke=getResources().getDimensionPixelSize(R.dimen.line_stroke);
		  bonus=new ArrayList<Bitmap>();
		  bonus.add(BitmapFactory.decodeResource(getResources(), R.drawable.invincibilita));
		  bonus.add(BitmapFactory.decodeResource(getResources(), R.drawable.allargalinea));
		  bonus.add(BitmapFactory.decodeResource(getResources(), R.drawable.punti));
		  bonus.add(BitmapFactory.decodeResource(getResources(), R.drawable.puntidoppi));
		  bonus.add(BitmapFactory.decodeResource(getResources(), R.drawable.skullbonusmuori));
		  bonus.add(BitmapFactory.decodeResource(getResources(), R.drawable.skullbonusrestringimentolinea));
		  bonus.add(BitmapFactory.decodeResource(getResources(), R.drawable.random));
		  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		  setContentView(v);
		 
		}

	@Override
	protected void onResume() {
		super.onResume();
		v.startGame(stroke, bonus);
	}

	@Override
	protected void onPause() {
		super.onPause();
		v.stopGame();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent(NewGame.this, MainMenu.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	
}
		








