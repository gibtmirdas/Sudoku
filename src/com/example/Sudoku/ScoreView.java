package com.example.Sudoku;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.example.Sudoku.db.HighScore;
import com.example.Sudoku.db.ScoreDB;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by thomas on 04/03/14.
 */
public class ScoreView extends Activity {

	private ListView listScore;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score);

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		ScoreDB db = new ScoreDB(this);
		db.open();
		// Tmp high score


		listScore = (ListView) findViewById(R.id.listScore);
		ArrayList<HashMap<String, String>> listItem;

		// Get scores from DB
		listItem = db.getAllScores();

		SimpleAdapter mSchedule = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.scoreitem,
				new String[]{"username", "score"}, new int[]{R.id.username, R.id.score});

		listScore.setAdapter(mSchedule);
		db.close();
	}
}