package com.example.Sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by gibtmirdas on 02/03/14.
 */
public class SudokuView extends View {

	private SudokuGrid grid;
	private int viewWidth, viewHeight;
	private Color background;
	private int xOffset=10, yOffset=10, delta=50;

	public SudokuView(Context context, AttributeSet attrs){
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		Paint dark = new Paint();
		dark.setColor(Color.BLACK);
		dark.setStrokeWidth(6);

		int strokeRayon = 5;

		if(viewHeight > viewWidth)
			delta = viewWidth / 10;
		else
			delta = viewHeight / 10;
		// vertical lines
		for(int i=0; i<10; i++){
			if(i%3==0){
				dark.setStrokeWidth(10);
			}else{
				dark.setStrokeWidth(6);
			}
			canvas.drawLine(xOffset + delta*i,yOffset-strokeRayon, xOffset + delta*i,strokeRayon+ yOffset + 9*delta,dark );
			canvas.drawLine(xOffset,yOffset + delta*i, xOffset + 9*delta,yOffset + delta*i,dark );
		}

		// horizontal lines
//		for(int j=0; j<10; j++){
//		}

		// Draw board

		// Exemple
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		viewWidth = w;
		viewHeight = h;
		Log.d("onSizeChanged", "Width:"+w+"--Height:"+h);
	}

	public void setGrid(SudokuGrid grid) {
		this.grid = grid;
	}
}
