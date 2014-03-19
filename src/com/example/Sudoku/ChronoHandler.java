package com.example.Sudoku;

import android.os.Message;
import android.util.Log;

/**
 * Created by gibtmirdas on 03/03/14.
 */
public class ChronoHandler {

	private long startTime = 0;
	private long totalTime = 0;

	public void handleMessage(Message msg) {
		if((Boolean)msg.getData().get("pause")){
			long tmp = System.currentTimeMillis();
			long delta = tmp - startTime;
			totalTime += delta;
		}else{
			startTime = System.currentTimeMillis();
		}
	}

	public long getTotal(){
		return (totalTime + (System.currentTimeMillis() - startTime))/1000;
	}
}