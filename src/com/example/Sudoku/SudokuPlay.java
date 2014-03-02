package com.example.Sudoku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
	}
}