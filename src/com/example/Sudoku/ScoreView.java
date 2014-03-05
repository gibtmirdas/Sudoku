package com.example.Sudoku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.example.Sudoku.db.HighScore;
import com.example.Sudoku.db.ScoreDB;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by thomas on 04/03/14.
 */
public class ScoreView extends Activity {

	private ListView listScore;
	private Menu m;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score);
		TextView txt = (TextView) findViewById(R.id.emptyScore);
		txt.setVisibility(TextView.VISIBLE);

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
		if(listItem.size() == 0)
			Log.d("LogCat", "DB Empty");

		db.close();

		TextView txt = (TextView) findViewById(R.id.emptyScore);
		if(listItem.size()==0){
			ListView list = (ListView) findViewById(R.id.listScore);
			list.setEmptyView(txt);
		}else{
			SimpleAdapter mSchedule = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.scoreitem,
					new String[]{"username", "score"}, new int[]{R.id.username, R.id.score});

			listScore.setAdapter(mSchedule);
		}
	}

	// Menu for resetting scores

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menuscore, menu);
		m = menu;
		return true;
	}

	@Override
	public boolean onOptionsItemSelected (MenuItem item)
	{
		switch(item.getItemId())
		{
			case R.id.resetScore:
				ScoreDB db = new ScoreDB(getApplicationContext());
				db.open();
				db.removeScoreAll();
				db.close();
				Intent i = new Intent(this, this.getClass());
				startActivity(i);
				finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
}