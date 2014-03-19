package com.example.Sudoku.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by thomas on 04/03/14.
 */
public class ScoreDB {

    private static final String NOM_BDD = "sudoku.db";

    private static final String TABLE_SCORE = "table_scores";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_SCORE = "score";
    private static final int NUM_COL_SCORE = 1;
	private static final String COL_USERNAME = "username";
	private static final int NUM_COL_USERNAME = 2;
	private static final String COL_DIFFICULTY = "difficulty";
	private static final int NUM_COL_DIFFICULTY = 3;

    private SQLiteDatabase bdd;

    private DatabaseHandler dbHandler;

    public ScoreDB(Context context){
        dbHandler = new DatabaseHandler(context, NOM_BDD, null, DatabaseHandler.VERSION);
    }

    public void open(){
        bdd = dbHandler.getWritableDatabase();
    }

    public void close(){
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

	public ArrayList<HashMap<String, String>> getAllScores(){
		Cursor c = bdd.query(TABLE_SCORE, new String[]{COL_ID, COL_SCORE, COL_USERNAME, COL_DIFFICULTY}, null, null, null, null, COL_SCORE );
		ArrayList<HashMap<String, String>> listItems = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map ;
		c.moveToFirst();
		if(c.getCount() == 0)
			return listItems;
		do{
			HighScore hs = new HighScore();
			hs.setId(c.getInt(NUM_COL_ID));
			hs.setScore(c.getInt(NUM_COL_SCORE));
			hs.setUserName(c.getString(NUM_COL_USERNAME));
			hs.setDifficulty(c.getInt(NUM_COL_DIFFICULTY));
			map = new HashMap<String, String>();
			map.put("username", hs.getUserName());
			map.put("score", ""+hs.getScore());
			map.put("difficulty", ""+hs.getDifficulty());
			listItems.add(map);
		}while(c.moveToNext());
		return listItems;
	}

    public long insertScore(HighScore hs){
        ContentValues values = new ContentValues();
        values.put(COL_SCORE, hs.getScore());
		values.put(COL_USERNAME, hs.getUserName());
		values.put(COL_DIFFICULTY, hs.getDifficulty());
        return bdd.insert(TABLE_SCORE, null, values);
    }

	public int removeScoreAll(){
		return bdd.delete(TABLE_SCORE, null, null);
	}

    private HighScore cursorToScore(Cursor c){
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        HighScore score = new HighScore();
        score.setId(c.getInt(NUM_COL_ID));
        score.setScore(c.getInt(NUM_COL_SCORE));
		score.setUserName(c.getString(NUM_COL_USERNAME));
		score.setDifficulty(c.getInt(NUM_COL_DIFFICULTY));
        c.close();
        return score;
    }
}
