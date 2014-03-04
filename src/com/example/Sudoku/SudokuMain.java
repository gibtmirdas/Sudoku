package com.example.Sudoku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.example.Sudoku.db.HighScore;

public class SudokuMain extends Activity implements View.OnClickListener{
	private Menu m = null;
	private int difficulty = 0;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		Button buttHigh = (Button) findViewById(R.id.ButtHighScore);
		Button buttStart = (Button) findViewById(R.id.ButtStart);
		// Set Background
		buttHigh.setBackgroundResource(R.drawable.buttonbackgrounds);
		buttStart.setBackgroundResource(R.drawable.buttonbackgrounds);

		// Set actions
		buttHigh.setOnClickListener(this);
		buttStart.setOnClickListener(this);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		m = menu;
		return true;
	}

	@Override
	public boolean onOptionsItemSelected (MenuItem item)
	{
		switch(item.getItemId())
		{
			case R.id.easy:
				difficulty = 0;
				return true;
			case R.id.medium:
				difficulty = 1;
				return true;
			case R.id.hard:
				difficulty = 2;
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.ButtStart){
			Intent i = new Intent(SudokuMain.this, SudokuPlay.class);
			i.putExtra("difficulty", difficulty);
			startActivity(i);
		}else if(v.getId() == R.id.ButtHighScore){
			Intent i = new Intent(SudokuMain.this, ScoreView.class);
			startActivity(i);
		}
	}
}
