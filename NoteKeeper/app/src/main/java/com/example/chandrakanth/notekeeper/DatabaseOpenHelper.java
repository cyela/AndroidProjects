package com.example.chandrakanth.notekeeper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Chandrakanth on 2/27/2017.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    static final String DB_NAME = "MyNoteKeeper.db";
    static final int DB_VERSION = 1;
    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        NoteTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        NoteTable.onUpgrade(db,oldVersion,newVersion);
    }
}
