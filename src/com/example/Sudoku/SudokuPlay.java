package com.example.Sudoku;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.drm.DrmStore;
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

    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sudoku);

		view = (SudokuView) findViewById(R.id.drawing_area);

		Intent i = getIntent();
		int difficulty = i.getIntExtra("difficulty", 0);
		Log.d("LogCat", "Difficulty : " + difficulty);

		grid = new SudokuGrid(difficulty);
		view.setGrid(grid);
		view.setPlay(this);
        handler = new ChronoHandler();
        msgB = new Bundle();

        msgB.putBoolean("pause", false);
        msg = new Message();
        msg.setData(msgB);
        handler.handleMessage(msg);
	}

	public void launchDialogue(){
		// 1. Instantiate an AlertDialog.Builder with its constructor
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final NumberPicker picker = new NumberPicker(builder.getContext());
		picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		picker.setMinValue(1);
		picker.setMaxValue(9);

		// 2. Chain together various setter methods to set the dialog characteristics
		builder.setTitle("Select Value");
		builder.setView(picker);
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				setPickedValue(picker.getValue());
			}
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				setPickedValue(0);
			}
		});

		// 3. Get the AlertDialog from create()
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	private void setPickedValue(int i){
		view.setPickedValue(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menugame, menu);
		m = menu;
		return true;
	}

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        switch(item.getItemId())
        {
            // Game paused
            case R.id.pause:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
                dialogPause();
                //msgB.clear();
                msgB.putBoolean("pause", true);
                msg.setData(msgB);
                Log.d("LogCat", "MSG created");
                handler.handleMessage(msg);
                Log.d("LogCat", "MSG sended");
                view.setPaused(true);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void dialogPause(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Pause");
        builder.setMessage("Game paused\nCurrent time:" + handler.getTotal() + "s");
        builder.setPositiveButton("Unpause",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                msgB.clear();
                msgB.putBoolean("pause", false);
                msg.setData(msgB);
                handler.handleMessage(msg);
                view.setPaused(false);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            }
        });
        AlertDialog dial = builder.create();
        dial.setCancelable(false);
        dial.show();
    }

	public void gameWon(){

		// TODO when game won => prompt alert and ask user to set username for highScore
		String username;
		float time;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Win !");
		builder.setMessage("You won the game\nCurrent time: " + handler.getTotal()+ " s");
		
		final EditText input = new EditText(this);
		builder.setView(input);

		builder.setPositiveButton("Save score",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String username = input.getText().toString();
				if(username == null || username.equals(""))
					gameWon();
				else{
					HighScore hs = new HighScore(handler.getTotal(),username );
					saveScore(hs);
					Intent i = new Intent(getApplicationContext(), SudokuMain.class);
					startActivity(i);
					finish();
				}
			}
		});
		builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
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

	private void saveScore(HighScore hs){
		ScoreDB db = new ScoreDB(getApplicationContext());
		db.open();
		db.insertScore(hs);
		db.close();
	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, SudokuMain.class);
		startActivity(i);
		finish();
	}
}