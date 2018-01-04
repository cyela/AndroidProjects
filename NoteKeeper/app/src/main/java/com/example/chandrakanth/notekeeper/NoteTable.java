package com.example.chandrakanth.notekeeper;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Chandrakanth on 2/27/2017.
 */

public class NoteTable {
//    id, note,
//    priority, update_time, status
    static final String TABLENAME = "NoteKeeper";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_NOTE = "note";
    static final String COLUMN_PRIORITY = "priority";
    static final String COLUMN_UPDATETIMAE = "update_time";
    static final String COLUMN_STATUS = "status";
    static public void onCreate(SQLiteDatabase db){

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " +TABLENAME+" (");
        sb.append(COLUMN_ID+ " integer primary key autoincrement, ");
        sb.append(COLUMN_NOTE+ " text not null, ");
        sb.append(COLUMN_PRIORITY+ " text not null, ");
        sb.append(COLUMN_UPDATETIMAE+ " text not null, ");
        sb.append(COLUMN_STATUS+ " text not null); ");


        db.execSQL(sb.toString());


    }
    static public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        NoteTable.onCreate(db);
    }


}
