package com.apgb.theline;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class GameOver extends Activity {
	TextView retry,scoreText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gameover);
		Intent i = getIntent();
		String score = i.getStringExtra("score");
		retry=(TextView) findViewById(R.id.retry);
		retry.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(GameOver.this, NewGame.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				
			}
		});
		scoreText=(TextView) findViewById(R.id.scoreText);
		scoreText.setText(score);
}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent(GameOver.this, MainMenu.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		
	}

}
