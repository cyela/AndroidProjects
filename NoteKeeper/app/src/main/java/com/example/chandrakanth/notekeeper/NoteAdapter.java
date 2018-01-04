package com.example.chandrakanth.notekeeper;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Chandrakanth on 2/27/2017.
 */

public class NoteAdapter extends ArrayAdapter<Note> {
    List<Note> mData;
    Context mContext;
    int mResource;
    int mtextType;
    DatabaseDataManager dm;
    public NoteAdapter(Context context, int resource, int textViewResourceId, List<Note> objects) {
        super(context, resource, textViewResourceId, objects);
        this.mData = objects;
        this.mContext = context;
        this.mResource = resource;
        this.mtextType = textViewResourceId;

    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource,parent,false);
        }

        final Note note=mData.get(position);
        Log.d("Note",note.toString());
        TextView textView=(TextView)convertView.findViewById(R.id.NoteDetails);
        TextView textP=(TextView)convertView.findViewById(R.id.textPriority);
        TextView textT=(TextView)convertView.findViewById(R.id.TextTime);
        textP.setText(note.getPriority());
        if(!note.getUpdate_time().equals(null)) {
            try {

                PrettyTime p = new PrettyTime();
                Date date1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(note.getUpdate_time());
                textT.setText(p.format(date1));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }else{
            textT.setText("");
        }
        dm = new DatabaseDataManager(convertView.getRootView().getContext());

        final CheckBox checkBox=(CheckBox)convertView.findViewById(R.id.checkBox);
        if(note.getStatus().equals("pending")){
            checkBox.setChecked(false);
        }
        else if(note.getStatus().equals("completed")){
            checkBox.setChecked(true);
        }
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()){
                    new AlertDialog.Builder(v.getRootView().getContext())
                            .setTitle("Do You  want to mark it as completed?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    note.setStatus("completed");
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                    Date date = new Date();
                                    note.setUpdate_time(dateFormat.format(date));
                                    dm.updateNote(note);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(mContext instanceof MainActivity){
                                        ((MainActivity)mContext).fillNoteList(mData);
                                    }
                                }
                            }).show();

                }
                else if(!checkBox.isChecked()) {
                    new AlertDialog.Builder(v.getRootView().getContext())
                            .setTitle("Do You  want to unmark it as pending?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    note.setStatus("pending");
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                    Date date = new Date();
                                    note.setUpdate_time(dateFormat.format(date));
                                    dm.updateNote(note);

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(mContext instanceof MainActivity){
                                        ((MainActivity)mContext).fillNoteList(mData);
                                    }
                                }
                            }).show();
                }
            }
        });

                textView.setText(note.getNote());

        return convertView;
    }
}
