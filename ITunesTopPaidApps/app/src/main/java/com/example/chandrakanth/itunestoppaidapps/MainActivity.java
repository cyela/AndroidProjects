package com.example.chandrakanth.itunestoppaidapps;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class MainActivity extends AppCompatActivity implements GetApplicationAsyncTask.IData {
    ProgressDialog progressBar;
    ArrayList<Application> AppArraylist;
    public static final String MyFavorites = "Myfav" ;
    final static String APPS_LIST="APPS_LIST";

    @Override
    protected void onResume() {
        new GetApplicationAsyncTask(MainActivity.this).execute(" https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json");
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.Refresh:
                new GetApplicationAsyncTask(MainActivity.this).execute(" https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json");
                return true;
            case R.id.Favorites:
                Log.d("result",AppArraylist.toString());
                Intent intent = new Intent(MainActivity.this,FavoriteActivity.class);
                intent.putExtra(MainActivity.APPS_LIST,AppArraylist);
                startActivity(intent);
                Log.d("item","selected");
                return true;
            case R.id.SortIncreasingly:
                Collections.sort(AppArraylist, new MainActivity.CustomComparator());
                fillAppName(AppArraylist);
                return true;
            case R.id.SortDecreasingly:
                Collections.sort(AppArraylist, new MainActivity.CustomComparator());
                Collections.reverse(AppArraylist);
                fillAppName(AppArraylist);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar= new ProgressDialog(MainActivity.this);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
        progressBar.show();

        SharedPreferences sharedpreferences = getSharedPreferences(MyFavorites, Context.MODE_PRIVATE);

        new GetApplicationAsyncTask(MainActivity.this).execute(" https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json");


    }
    public void fillAppName(final ArrayList<Application> Applist) {


        ListView listView = (ListView)findViewById(R.id.LVView);
        ApplicationAdapter adapter = new ApplicationAdapter(this,R.layout.row_layout,android.R.id.text1,Applist);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
        progressBar.dismiss();


    }


    @Override
    public void setupData(ArrayList<Application> result) {
        if(result!=null)
        {

            AppArraylist=result;
            fillAppName(result);

        }
    }



    private class CustomComparator implements Comparator<Application> {
        @Override
        public int compare(Application o1, Application o2) {
            return (o1.getAppPrice()==o2.getAppPrice())?0:(o1.getAppPrice()>o2.getAppPrice())?1:-1 ;
        }
    }
}
