package com.example.chandrakanth.notekeeper;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Spinner spinner;
    Button addBtn;
    ListView listView;
    DatabaseDataManager dm;
    List<Note> notesList;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ShowAll:

                notesList = dm.getAllNotes();
                fillNoteList(notesList);
                return true;
            case R.id.ShowComp:
                notesList = dm.getAllNotes();
                ArrayList<Note> comNoteList=new ArrayList<>();
                for(int i=0;i<notesList.size();i++){
                    if(notesList.get(i).getStatus().equals("completed")){
                        comNoteList.add(notesList.get(i));
                    }
                }
                fillNoteList(comNoteList);
                return true;
            case R.id.ShowPend:
                notesList = dm.getAllNotes();
                ArrayList<Note> penNoteList=new ArrayList<>();
                for(int i=0;i<notesList.size();i++){
                    if(notesList.get(i).getStatus().equals("pending")){
                        penNoteList.add(notesList.get(i));
                    }
                }
                fillNoteList(penNoteList);
                return true;
            case R.id.SortTime:
                notesList = dm.getAllNotes();
                fillNoteList(notesList);
                Collections.sort(notesList,new Comparator<Note>() {
                    @Override
                    public int compare(Note s1, Note s2) {
                        return s1.getUpdate_time().compareToIgnoreCase(s2.getUpdate_time());
                    }
                });
                fillNoteList(notesList);
                return true;

        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.spinnerPriroty);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        final EditText editText=(EditText)findViewById(R.id.NoteName);
        addBtn=(Button)findViewById(R.id.button);
        listView = (ListView)findViewById(R.id.ListView);
        listView.setLongClickable(true);
        dm = new DatabaseDataManager(MainActivity.this);
        notesList = dm.getAllNotes();
        if(!notesList.isEmpty()) {
            fillNoteList(notesList);
        }
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Notepriority = spinner.getSelectedItem().toString();
                String Notename=editText.getText().toString();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                Log.d("Dagte",dateFormat.format(date));
                dm.saveNote(new Note(Notename,Notepriority,"pending",dateFormat.format(date)));
                notesList = dm.getAllNotes();
                fillNoteList(notesList);
                editText.setText("");


            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(view.getRootView().getContext())
                        .setTitle("Do You  want to delete the note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dm.delNote(notesList.get(position));
                                Toast.makeText(MainActivity.this,"Deleted successfully",Toast.LENGTH_SHORT).show();
                                notesList = dm.getAllNotes();
                                fillNoteList(notesList);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();


                return true;
            }
        });

    }
    public void fillNoteList(List<Note> noteList){

        NoteAdapter adapter = new NoteAdapter(MainActivity.this,R.layout.rowlayout,android.R.id.text1,noteList);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
    }
}
