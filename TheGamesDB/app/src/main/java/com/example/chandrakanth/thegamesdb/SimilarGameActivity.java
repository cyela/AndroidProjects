package com.example.chandrakanth.thegamesdb;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class SimilarGameActivity extends AppCompatActivity implements getGamesListAsync.Idata {
    ArrayList<String> Sid;
    LinearLayout linearLayout;
    TextView textView;
    String BaseUrl = "http://thegamesdb.net/api/GetGamesList.php?name=";
    ArrayList<GetGame> GLArrayList;
    ProgressDialog progressSimilar;

    String BaseUrl2 = "http://thegamesdb.net/api/GetGame.php?id=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar_game);
        linearLayout=(LinearLayout)findViewById(R.id.SimilarLayout);
        linearLayout.removeAllViews();

        TextView txtView = (TextView) findViewById(R.id.textView);
        txtView.setText(getIntent().getExtras().getString("Title"));
        String Key=getIntent().getExtras().getString("Key");
        Log.d("demo6",Key);
        new getGamesListAsync(SimilarGameActivity.this).execute(BaseUrl + Key);

        progressSimilar =new ProgressDialog(this);
        progressSimilar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressSimilar.setCancelable(false);
        progressSimilar.show();

        Button btnfinish=(Button)findViewById(R.id.buttonFinish);
        btnfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                linearLayout.removeAllViews();
            }
        });
    }
    private void fillGameList(final ArrayList<GetGame> gamesList)
    {
        if(gamesList!=null) {
            Log.d("demo8","entered inside");

            Sid=new ArrayList<String>();
            Sid.addAll(getIntent().getExtras().getStringArrayList("idSet"));
            for(int i=0;i<Sid.size();i++){
                for(int j=0;j<gamesList.size();j++){
                   if(Sid.get(i).equals(gamesList.get(j).getId()))
                   {
                     textView=new TextView(this);
                       textView.setText(gamesList.get(j).getGTitle()+",Released on: "+gamesList.get(j).getReleaseDate()+",Platform : "+gamesList.get(j).getPlatform());
                       textView.setTypeface(null, Typeface.BOLD);
                       textView.setTextSize(15);
                       linearLayout.addView(textView);

                   }
                }
            }
        }

    }
    @Override
    public void setupData(ArrayList<GetGame> result) {
        GLArrayList=result;
        fillGameList(GLArrayList);
        Log.d("demo7",GLArrayList.toString());
        progressSimilar.dismiss();
    }
}


