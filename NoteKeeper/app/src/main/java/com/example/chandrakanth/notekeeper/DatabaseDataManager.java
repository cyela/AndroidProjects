package com.example.chandrakanth.notekeeper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by Chandrakanth on 2/27/2017.
 */

public class DatabaseDataManager {

    private Context mContext;
    private DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private NoteDAO noteDAO;

    public DatabaseDataManager(Context mContext) {
        this.mContext = mContext;
        dbOpenHelper = new DatabaseOpenHelper(this.mContext);
        db = dbOpenHelper.getWritableDatabase();
        noteDAO = new NoteDAO(db);
    }

    public void close(){
        if(db!=null){
            db.close();
        }
    }
    public long saveNote(Note note){
        return this.noteDAO.save(note);
    }
    public boolean delNote(Note note){
        return this.noteDAO.delete(note);
    }

    public boolean updateNote(Note note){return this.noteDAO.update(note);}
    public List<Note> getAllNotes(){
        return this.noteDAO.getAll();
    }


}
