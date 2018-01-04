package com.example.chandrakanth.notekeeper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chandrakanth on 2/27/2017.
 */

public class NoteDAO {
    private SQLiteDatabase db;
    private static final String[] allColumns = {
            NoteTable.COLUMN_ID,
            NoteTable.COLUMN_NOTE,
            NoteTable.COLUMN_PRIORITY,
            NoteTable.COLUMN_UPDATETIMAE,
            NoteTable.COLUMN_STATUS};

    public NoteDAO(SQLiteDatabase db) {
        this.db = db;
    }
    public long save(Note note){
        ContentValues values = new ContentValues();
        values.put(NoteTable.COLUMN_NOTE,note.getNote());
        values.put(NoteTable.COLUMN_PRIORITY,note.getPriority());
        values.put(NoteTable.COLUMN_STATUS,note.getStatus());
        values.put(NoteTable.COLUMN_UPDATETIMAE, note.getUpdate_time());
        return db.insert(NoteTable.TABLENAME,null,values);

    }
    public boolean update(Note note){
        ContentValues values = new ContentValues();
        values.put(NoteTable.COLUMN_NOTE,note.getNote());
        values.put(NoteTable.COLUMN_PRIORITY,note.getPriority());
        values.put(NoteTable.COLUMN_STATUS,note.getStatus());
        values.put(NoteTable.COLUMN_UPDATETIMAE, String.valueOf(note.getUpdate_time()));
        return db.update(NoteTable.TABLENAME,values,NoteTable.COLUMN_ID + "=?",new String[]{note.getId()+""})>0;


    }
    public boolean delete(Note note){
        return db.delete(NoteTable.TABLENAME,NoteTable.COLUMN_ID + "=?",new String[]{note.getId()+""})>0;
    }

    public Note get(long id){
        Note note = null;
        Cursor c = db.query(true,NoteTable.TABLENAME,allColumns, NoteTable.COLUMN_ID + "=?",new String[]{id+""},null,null,null,null,null);
        if(c!=null && c.moveToFirst() ){
            note = buildNoteFromCursor(c);
        }
        if(!c.isClosed()){
            c.close();
        }
        return note;
    }

    public List<Note> getAll(){
        List<Note> noteList = new ArrayList<Note>();

        Cursor c = db.query(NoteTable.TABLENAME,allColumns, null,null,null,null,null);

        if(c!=null && c.moveToFirst()){
            do{
                Note note = buildNoteFromCursor(c);
                if(note!=null){
                    noteList.add(note);
                }
            }while (c.moveToNext());
            if(!c.isClosed()){
                c.close();
            }
        }
        return noteList;
    }

    private Note buildNoteFromCursor(Cursor c){
        Note note = null;
        if(c!=null){
            note = new Note();
            note.setId(c.getInt(0));
            note.setNote(c.getString(1));
            note.setPriority(c.getString(2));
            note.setUpdate_time(c.getString(3));
            note.setStatus(c.getString(4));

        }
        return note;
    }



}
