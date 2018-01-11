package com.example.chandrakanth.itunestoppaidapps;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArraySet;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Set;


public class FavoriteActivity extends AppCompatActivity {
    Button btnFinish;
    ListView listViewFavorites;
    ArrayList<Application> appArrayList,favorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite2);
        btnFinish =(Button)findViewById(R.id.btnFinish);
        btnFinish.setBackgroundColor(ContextCompat.getColor(FavoriteActivity.this, R.color.colorPrimary));

        listViewFavorites=(ListView)findViewById(R.id.listViewFavorites);

        appArrayList = new ArrayList<>();
        favorites = new ArrayList<>();

        if(getIntent().getExtras()!=null){
            appArrayList = (ArrayList<Application>)getIntent().getExtras().getSerializable(MainActivity.APPS_LIST);

            final SharedPreferences myPrefs = getSharedPreferences("com.example.chandrakanth.itunestoppaidapps", MODE_PRIVATE);
            final Set<String> nullSet = new ArraySet<String>();
            final Set<String> Set = myPrefs.getStringSet("AppFavorite",nullSet);
            for (Application app:appArrayList)
            {
                if(Set.contains(app.getAppName()))
                    favorites.add(app);
            }

            FillAppsList(favorites);
        }

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void FillAppsList(final ArrayList<Application> appsList)
    {
        ApplicationAdapter adapter = new ApplicationAdapter(this,R.layout.row_layout,android.R.id.text1,appsList);
        listViewFavorites.setAdapter(adapter);
        adapter.setNotifyOnChange(true);

    }
}




