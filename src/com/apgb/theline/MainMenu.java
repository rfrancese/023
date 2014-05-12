package com.apgb.theline;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;




public class MainMenu extends Activity {
	
	public class newGameListner implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			Intent intent;
			intent = new Intent(MainMenu.this, NewGame.class);
			startActivity(intent);
			
		}
		
	}
	
	TextView newGame;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		newGame=(TextView) findViewById(R.id.retry);
		newGame.setOnClickListener(new newGameListner());
		
}
}
