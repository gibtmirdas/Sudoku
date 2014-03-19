package com.example.Sudoku;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import com.example.Sudoku.db.HighScore;
import com.example.Sudoku.db.ScoreDB;

/**
 * Created by gibtmirdas on 02/03/14.
 */
public class SudokuPlay extends Activity {

	private SudokuGrid grid;
	private SudokuView view;
	private Menu m;
	private ChronoHandler handler;
	private Bundle msgB;
	private Message msg;
	private int difficulty;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sudoku);
		view = (SudokuView) findViewById(R.id.drawing_area);
		Intent i = getIntent();
		// 2 case of declaring the grid =>
		// 1- with 'content' Extra => db grid
		difficulty = i.getIntExtra("difficulty", 0);
		if (i.hasExtra("content")) {
			String content = i.getStringExtra("content");
			grid = new SudokuGrid(content);
		}
		// 2- Whithout 'content' => grid based on difficulty, hardcoded in SudokuGrid
		else {
			grid = new SudokuGrid(difficulty);
		}
		view.setGrid(grid);
		view.setPlay(this);
		handler = new ChronoHandler();
		msgB = new Bundle();
		msg = new Message();
		sendPause(false);
		handler.handleMessage(msg);
	}

	public void launchDialogue() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final NumberPicker picker = new NumberPicker(builder.getContext());
		picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		picker.setMinValue(1);
		picker.setMaxValue(9);

		builder.setTitle("Select Value");
		builder.setView(picker);
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				setPickedValue(picker.getValue());
			}
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				setPickedValue(-1);
			}
		});

		AlertDialog dialog = builder.create();
		dialog.show();
	}

	private void setPickedValue(int i) {
		view.setPickedValue(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menugame, menu);
		m = menu;
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			// Game paused
			case R.id.pause:
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
				dialogPause();
				sendPause(true);
				view.setPaused(true);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void dialogPause() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Pause");
		builder.setMessage("Game paused\nCurrent time:" + handler.getTotal() + "s");
		builder.setPositiveButton("Unpause", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				sendPause(false);
				view.setPaused(false);
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
			}
		});
		AlertDialog dial = builder.create();
		dial.setCancelable(false);
		dial.show();
	}

	public void gameWon() {
		final float time = handler.getTotal();
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Win !");
		builder.setMessage("You won the game\nCurrent time: " + handler.getTotal() + " s");

		final EditText input = new EditText(this);
		builder.setView(input);

		builder.setPositiveButton("Save score", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String username = input.getText().toString();
				if (username == null || username.equals(""))
					gameWon();
				else {
					sendPause(true);
					HighScore hs = new HighScore(time, username, difficulty);
					saveScore(hs);
					Intent i = new Intent(getApplicationContext(), SudokuMain.class);
					startActivity(i);
					finish();
				}
			}
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent i = new Intent(getApplicationContext(), SudokuMain.class);
				startActivity(i);
				finish();
			}
		});
		AlertDialog dial = builder.create();
		dial.setCancelable(false);
		dial.show();
	}

	private void saveScore(HighScore hs) {
		ScoreDB db = new ScoreDB(getApplicationContext());
		Log.d("logcat", "HS:" + hs.getUserName());
		db.open();
		Log.d("logcat", "HHHHH");
		db.insertScore(hs);
		db.close();
	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, SudokuMain.class);
		startActivity(i);
		finish();
	}

	public void sendPause(boolean isPause) {
		msgB.clear();
		msgB.putBoolean("pause", isPause);
		msg.setData(msgB);
		handler.handleMessage(msg);
	}
}