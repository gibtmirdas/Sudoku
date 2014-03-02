package com.example.Sudoku;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.NumberPicker;

/**
 * Created by gibtmirdas on 02/03/14.
 */
public class SudokuPlay extends Activity {

	private SudokuGrid grid;
	private SudokuView view;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sudoku);

		view = (SudokuView) findViewById(R.id.drawing_area);

		Intent i = getIntent();
		int difficulty = i.getIntExtra("difficulty", 0);
		Log.d("Difficulty", "" + difficulty);

		grid = new SudokuGrid(difficulty);
		view.setGrid(grid);
		view.setPlay(this);
	}

	public void launchDialogue(){
		// 1. Instantiate an AlertDialog.Builder with its constructor
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final NumberPicker picker = new NumberPicker(builder.getContext());
		picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		picker.setMinValue(0);
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
				setPickedValue(-1);
			}
		});

		// 3. Get the AlertDialog from create()
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	private void setPickedValue(int i){
		view.setPickedValue(i);
	}
}