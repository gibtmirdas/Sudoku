package com.example.Sudoku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.example.Sudoku.db.Grid;
import com.example.Sudoku.db.GridDB;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by gibtmirdas on 06.03.14.
 */
public class SudokuGrids extends Activity implements AdapterView.OnItemClickListener{

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
		listGrids = (ListView) findViewById(R.id.list_grids);
		listGrids.setOnItemClickListener(this);
		ArrayList<HashMap<String, String>> listItem;
		listItem = db.getAllGrides();
		db.close();
		TextView txt = (TextView) findViewById(R.id.empty_grid);
		if(listItem.size()==0){
			ListView list = (ListView) findViewById(R.id.list_grids);
			list.setEmptyView(txt);
		}else{
			SimpleAdapter mSchedule = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.grid_item,
					new String[]{"ID", "grid", "difficulty"}, new int[]{R.id.grid_id,R.id.grid_content, R.id.grid_difficulty});

			listGrids.setAdapter(mSchedule);
		}
	}

	/**
	 * Menu to call Android explorer and import new grid
	 * @param menu
	 * @return
	 */
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
			// Get file path to import
			if(resultCode == RESULT_OK){
				String result=data.getStringExtra("path");
				String content = "";
				try{
					BufferedReader br = new BufferedReader(new FileReader(result));
					StringBuilder sb = new StringBuilder();
					String line = br.readLine();

					while (line != null) {
						sb.append(line);
						sb.append("-");
						line = br.readLine();
					}
					content = sb.toString();
				}catch (Exception e){}
				Log.d("logcat",content);
				// Save new grid into DB
				if(content.length() == (9*9)+3){
					int difficulty = Integer.parseInt(content.split("-")[0]);
					content = content.split("-")[1];
					Grid grid = new Grid(content,difficulty);
					GridDB gridDB = new GridDB(this);
					gridDB.open();
					gridDB.insertGrid(grid);
					gridDB.close();
					Intent i = new Intent(getApplicationContext(), this.getClass());
					startActivity(i);
					finish();
					Log.d("logcat","FUUUUUCK");
				}
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Grid g;
		GridDB db = new GridDB(getApplicationContext());
		db.open();
		g = db.getGrid(position + 1);
		db.close();
		// Run SudokuPlay activity
		Intent i = new Intent(getApplicationContext(), SudokuPlay.class);
		i.putExtra("content", g.getGrid());
		i.putExtra("difficulty", g.getDifficulty());
		startActivity(i);
		finish();
	}
}