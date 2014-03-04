package com.example.Sudoku.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static final String TABLE_SCORE = "table_scores";
	private static final String COL_ID = "ID";
    private static final String COL_SCORE = "score";
    private static final String COL_USERNAME = "username";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_SCORE + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_SCORE + " INTEGER NOT NULL, "
            + COL_USERNAME + " TEXT NOT NULL);";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //on créé la table à partir de la requête écrite dans la variable CREATE_BDD
        db.execSQL(CREATE_BDD);
		Log.d("LogCat","Table Created");
	}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On peut fait ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        db.execSQL("DROP TABLE " + TABLE_SCORE + ";");
        onCreate(db);
    }

}