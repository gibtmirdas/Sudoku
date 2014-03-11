package com.example.Sudoku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.example.Sudoku.db.GridDB;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gibtmirdas on 06.03.14.
 */
public class SudokuGrids extends Activity {

	private ListView listGrids;
	private Menu m;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grids);
		TextView txt = (TextView) findViewById(R.id.empty_grid);

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		GridDB db = new GridDB(this);
		db.open();
		// Tmp high score


		listGrids = (ListView) findViewById(R.id.listScore);
		ArrayList<HashMap<String, String>> listItem;

		// Get scores from DB
		listItem = db.getAllGrides();

		db.close();

		TextView txt = (TextView) findViewById(R.id.empty_grid);
		if(listItem.size()==0){
			ListView list = (ListView) findViewById(R.id.listScore);
			list.setEmptyView(txt);
		}else{
			SimpleAdapter mSchedule = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.grid_item,
					new String[]{"grid", "difficulty"}, new int[]{R.id.grid_id, R.id.grid_difficulty});

			listGrids.setAdapter(mSchedule);
		}
	}

	// Menu for resetting scores

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_grids, menu);
		m = menu;
		return true;
	}

	@Override
	public boolean onOptionsItemSelected (MenuItem item)
	{
		switch(item.getItemId())
		{
			case R.id.import_grid:
				Intent i2 = new Intent(getApplicationContext(), AndroidExplorer.class);
				startActivityForResult(i2, 1);

				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {

			if(resultCode == RESULT_OK){
				String result=data.getStringExtra("result");
				Log.d("LogCat", "Get Result from AndroidExplorer");
			}
			if (resultCode == RESULT_CANCELED) {
				Log.d("LogCat", "NOO Result from AndroidExplorer");
			}
		}
	}
}