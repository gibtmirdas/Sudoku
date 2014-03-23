package com.example.Sudoku;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class SudokuView extends View {

    private SudokuGrid grid;
    private SudokuPlay play;
    private int viewWidth, viewHeight;
    private int xOffset = 10, yOffset = 10, delta = 50;
    private Rect[][] rects = new Rect[9][9];
    private Point currentChoose;
    private int initial[][] = new int[9][9];
    private boolean isPaused = false;

    public SudokuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        currentChoose = new Point(0, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint dark = new Paint();
        Paint charPaint = new Paint();
        Paint charOld = new Paint();
        Paint rectPaint = new Paint();
        dark.setColor(Color.BLACK);
        dark.setStrokeWidth(6);
        charPaint.setColor(getResources().getColor(R.color.newInput));
        charOld.setColor(getResources().getColor(R.color.foreground));
        rectPaint.setColor(getResources().getColor(R.color.selected));

        int strokeRayon = 5;
        if (viewHeight > viewWidth)
            delta = viewWidth;
		else
            delta = viewHeight;
        delta = (delta - 2 * 10) / 9;
		if (viewHeight > viewWidth){
            xOffset = 10;
			yOffset = (viewHeight /2) - ((9*delta)/2);
        }else{
			xOffset = (viewWidth /2) - ((9*delta)/2);
            yOffset = 10;
        }


        charPaint.setTextSize(50);
        charOld.setTextSize(50);

        // Print selection Rect
        if (currentChoose.x >= 0) {
            canvas.drawRect(new Rect(xOffset + delta * currentChoose.y, yOffset + delta * currentChoose.x,
                    xOffset + delta * (currentChoose.y + 1), yOffset + delta * (currentChoose.x + 1)), rectPaint);
        }

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
        float xTxt, yTxt;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                rects[i][j] = new Rect(xOffset + delta * j, yOffset + delta * i, xOffset + delta * (j + 1), yOffset + delta * (i + 1));
                xTxt = (xOffset + delta * (j + 1) + xOffset + delta * j) / 2 - charPaint.getTextSize() / 4;
                yTxt = (yOffset + delta * (i + 1) + yOffset + delta * i) / 2 + charPaint.getTextSize() / 3;
                if (!isPaused) {
                    if (initial[i][j] != 0) {
                        canvas.drawText("" + grid.getValueAt(i, j), xTxt, yTxt, charOld);
                    } else {
                        if (grid.getValueAt(i, j) != 0)
                            canvas.drawText("" + grid.getValueAt(i, j), xTxt, yTxt, charPaint);
                    }
                }else{
                    canvas.drawText("-", xTxt, yTxt, charOld);
                }
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
    }

    /* **********************************************
     * 				 Manage Selection				*
	 ***********************************************/

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            int x = (int) event.getX(), y = (int) event.getY();
            clicked(x, y);
        }
        return true;
    }

	/**
	 * Manage non-tactile devices, using pad
	 * @param keyCode
	 * @param event
	 * @return
	 */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                currentChoose.y = (currentChoose.y - 1) % 9;
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                currentChoose.y = (currentChoose.y + 1) % 9;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                currentChoose.y = (currentChoose.x - 1) % 9;
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                currentChoose.y = (currentChoose.x + 1) % 9;
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                clicked(currentChoose.x, currentChoose.y);
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void clicked(int x, int y) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (rects[i][j].contains(x, y)) {// Creating alert Dialog with one Button
                    if (initial[i][j] == 0) {
                        currentChoose.set(i, j);
                        this.invalidate();
                        play.launchDialogue();
                    }
                }
            }
        }
    }

    public void played(int value, int x, int y) {
        // if case editable ( == 0 in initial grid)
        if (initial[x][y] == 0) {
            // if case empty => add if possible
            if (grid.getValueAt(x, y) == 0) {
                if (grid.canAddAt(value, x, y)) {
                    grid.addAt(value, x, y);
                }
                // case already edited (replace value)
            } else {
                updateValue(value, x, y);
            }
            this.invalidate();
        }
        if (grid.isFinished()) {
			play.gameWon();
        }
    }

    public void updateValue(int value, int x, int y) {
        int oldValue = grid.getValueAt(x, y);
        grid.deleteAt(x, y);
        if (grid.canAddAt(value, x, y))
            grid.addAt(value, x, y);
        else
            grid.addAt(oldValue, x, y);
    }

	/* **********************************************
     * 				    Gets / Sets		  		    *
	 ***********************************************/

    public void setGrid(SudokuGrid grid) {
        this.grid = grid;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                initial[i][j] = grid.getValueAt(i, j);
            }
        }
    }

    public void setPlay(SudokuPlay play) {
        this.play = play;
    }

    public void setPickedValue(int pickedValue) {
		if(pickedValue>0)
        	played(pickedValue, currentChoose.x, currentChoose.y);
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
        this.invalidate();
    }
}
