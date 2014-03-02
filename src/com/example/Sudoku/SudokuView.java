package com.example.Sudoku;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by gibtmirdas on 02/03/14.
 */
public class SudokuView extends View {

	private SudokuGrid grid;
	private SudokuPlay play;
	private int viewWidth, viewHeight;
	private Color background;
	private int xOffset = 10, yOffset = 10, delta = 50;
	private Rect[][] rects = new Rect[9][9];

	public SudokuView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		Paint dark = new Paint();
		dark.setColor(Color.BLACK);
		dark.setStrokeWidth(6);

		int strokeRayon = 5;

		if (viewHeight > viewWidth)
			delta = viewWidth / 10;
		else
			delta = viewHeight / 10;
		// Draw grid
		for (int i = 0; i < 10; i++) {
			if (i % 3 == 0) {
				dark.setStrokeWidth(10);
			} else {
				dark.setStrokeWidth(6);
			}
			canvas.drawLine(xOffset + delta * i, yOffset - strokeRayon, xOffset + delta * i, strokeRayon + yOffset + 9 * delta, dark);
			canvas.drawLine(xOffset, yOffset + delta * i, xOffset + 9 * delta, yOffset + delta * i, dark);
		}
		// Place rects to identify which case the user clicked on
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				rects[i][j] = new Rect(xOffset + delta * j, yOffset + delta * i, xOffset + delta * (j + 1), yOffset + delta * (i + 1));
			}
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		viewWidth = w;
		viewHeight = h;
		Log.d("onSizeChanged", "Width:" + w + "--Height:" + h);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			int x = (int) event.getX(), y = (int) event.getY();

			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (rects[i][j].contains(x, y)) {// Creating alert Dialog with one Button
						play.launchDialogue();
					}
				}
			}
		}
		return true;
	}

	public void setGrid(SudokuGrid grid) {
		this.grid = grid;
	}

	public void setPlay(SudokuPlay play) {
		this.play = play;
	}

	public void setPickedValue(int pickedValue) {
		Log.d("Picked value", "" + pickedValue);
	}
}
