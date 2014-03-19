package com.example.Sudoku.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	public static int VERSION = 4;

	private static final String TABLE_SCORE = "table_scores";
	private static final String COL_ID = "ID";
	private static final String COL_SCORE = "score";
	private static final String COL_USERNAME = "username";
	private static final String COL_DIFFICULTY_HS = "difficulty";

	private static final String TABLE_GRID = "table_grid";
	private static final String COL_GRID = "grid";
	private static final String COL_DIFFICULTY = "difficulty";




	private static final String CREATE_BDD = "CREATE TABLE " + TABLE_SCORE + " ("
			+ COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_SCORE + " INTEGER NOT NULL, "
			+ COL_USERNAME + " TEXT NOT NULL, "+COL_DIFFICULTY_HS+" INTEGER NOT NULL);";

	private static final String CREATE_BDD_GRID = "CREATE TABLE " + TABLE_GRID + " ("
			+ COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_GRID + " TEXT NOT NULL, "
			+ COL_DIFFICULTY + " INTEGER NOT NULL);";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_BDD);
		db.execSQL(CREATE_BDD_GRID);
	}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE " + TABLE_SCORE + ";");
		db.execSQL("DROP TABLE " + TABLE_GRID + ";");
        onCreate(db);
    }

}