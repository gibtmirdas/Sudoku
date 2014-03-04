package com.example.Sudoku;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.text.DecimalFormat;

/**
 * Created by gibtmirdas on 03/03/14.
 */
public class ChronoHandler {

	private long startTime = 0;
	private long totalTime = 0;

	public void handleMessage(Message msg) {
        Log.d("LogCat","HANDLER GET MSG");
		if((Boolean)msg.getData().get("pause")){
			long tmp = System.currentTimeMillis();
			long delta = tmp - startTime;
			totalTime += delta;
            Log.d("LogCat","Paused");
		}else{
			startTime = System.currentTimeMillis();
            Log.d("LogCat", "UNpaused : "+startTime);
		}
	}

	public String getTotal(){
        DecimalFormat formatter = new DecimalFormat("#,###.0");
		return formatter.format((totalTime + (System.currentTimeMillis() - startTime))/1000.0);
	}
}
