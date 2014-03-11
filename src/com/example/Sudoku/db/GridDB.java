package com.example.Sudoku.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by thomas on 04/03/14.
 */
public class GridDB {

    private static final String NOM_BDD = "sudoku.db";

    private static final String TABLE_GRID = "table_grid";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_GRID = "grid";
    private static final int NUM_COL_GRID = 1;
    private static final String COL_DIFFICULTY = "difficulty";
    private static final int NUM_COL_DIFFICULTY = 2;

    private SQLiteDatabase bdd;

    private DatabaseHandler dbHandler;

    public GridDB(Context context){
        dbHandler = new DatabaseHandler(context, NOM_BDD, null, DatabaseHandler.VERSION);
    }

    public void open(){
		close();
        bdd = dbHandler.getWritableDatabase();
    }

    public void close(){
		if(bdd != null)
        	bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

	public ArrayList<HashMap<String, String>> getAllGrides(){
		Cursor c = bdd.query(TABLE_GRID, new String[]{COL_ID, COL_GRID, COL_DIFFICULTY}, null, null, null, null, COL_GRID);
		ArrayList<HashMap<String, String>> listItems = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map ;
		c.moveToFirst();
		if(c.getCount() == 0)
			return listItems;
		do{
			Grid hs = new Grid();
			hs.setId(c.getInt(NUM_COL_ID));
			hs.setGrid(c.getString(NUM_COL_GRID));
			hs.setDifficulty(c.getInt(NUM_COL_DIFFICULTY));
			map = new HashMap<String, String>();
			map.put("grid", hs.getGrid());
			map.put("difficulty", ""+hs.getDifficulty());
			listItems.add(map);
		}while(c.moveToNext());
		return listItems;
	}

    public long insertGrid(Grid g){
        ContentValues values = new ContentValues();
        values.put(COL_GRID, g.getGrid());
        values.put(COL_DIFFICULTY, g.getDifficulty());
        return bdd.insert(TABLE_GRID, null, values);
    }

    public int updateGrid(int id, Grid g){
        ContentValues values = new ContentValues();
        values.put(COL_GRID, g.getGrid());
        values.put(COL_DIFFICULTY, g.getDifficulty());
        return bdd.update(TABLE_GRID, values, COL_ID + " = " +id, null);
    }

	public int removeGridWithID(int id){
		return bdd.delete(TABLE_GRID, COL_ID + " = " +id, null);
	}

	public int removeGridAll(){
		return bdd.delete(TABLE_GRID, null, null);
	}

    public Grid getGrid(int id){
        Cursor c = bdd.query(TABLE_GRID, new String[] {COL_ID, COL_GRID, COL_DIFFICULTY}, COL_GRID + " = \"" + id +"\"", null, null, null, null);
        return cursorToScore(c);
    }

    private Grid cursorToScore(Cursor c){
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        Grid score = new Grid();
        score.setId(c.getInt(NUM_COL_ID));
        score.setGrid(c.getString(NUM_COL_GRID));
        score.setDifficulty(c.getInt(NUM_COL_DIFFICULTY));
        c.close();
        return score;
    }
}
