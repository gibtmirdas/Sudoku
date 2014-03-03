package com.example.Sudoku;

import android.os.Handler;
import android.os.Message;

/**
 * Created by gibtmirdas on 03/03/14.
 */
public class ChronoHandler extends Handler{

	long startTime = 0;
	long totalTime = 0;

	@Override
	public void handleMessage(Message msg) {
		if((Boolean)msg.getData().get("pause")){
			long tmp = System.currentTimeMillis();
			long delta = tmp - startTime;
			totalTime += delta;
		}else{
			startTime = System.currentTimeMillis();
		}
	}

	public float getTotal(){
		return totalTime + (System.currentTimeMillis() - startTime);
	}
}
