package com.example.chandrakanth.thegamesdb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements getGamesListAsync.Idata {
    String BaseUrl = "http://thegamesdb.net/api/GetGamesList.php?name=";
    String BaseUrl2 = "http://thegamesdb.net/api/GetGame.php?id=";
    String KeyId;
    String Key;
    String gid;
    int counter;
    ArrayList<GetGame> GLArrayList;
    RadioButton radioButton;
    ProgressDialog progressmain;
    Button goBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText searchET = (EditText) findViewById(R.id.IPSearchGame);
        final Button searchBtn = (Button) findViewById(R.id.BtnSearch);
        searchBtn.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
        final ScrollView scrollView = (ScrollView) findViewById(R.id.ScvLayout);
        goBtn = (Button) findViewById(R.id.BtnGo);

        goBtn.setEnabled(false);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressmain =new ProgressDialog(MainActivity.this);
                progressmain.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressmain.setCancelable(false);
                progressmain.show();
                Key = searchET.getText().toString();
                new getGamesListAsync(MainActivity.this).execute(BaseUrl + Key);
                goBtn.setEnabled(true);
            }
        });

        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,GameDetailActivity.class);
                intent.putExtra("url",BaseUrl2+gid);
                intent.putExtra("SearchedGame",searchET.getText().toString());
                startActivity(intent);



            }
        });

    }


    private void fillGameList(final ArrayList<GetGame> gamesList)
    {

        if(gamesList!=null) {

            LinearLayout linearLayout=(LinearLayout)findViewById(R.id.scrollVewLayout);
            linearLayout.removeAllViews();
           final  int length=gamesList.size();

            final RadioGroup rdGrp=new RadioGroup(MainActivity.this);
            rdGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton rdBtn=(RadioButton)findViewById(checkedId);
                    Log.d("demo","checked the"+rdBtn.getText().toString());
                    int x = rdBtn.getId();
                    gid=gamesList.get(x).getId();
                    Log.d("demo",gid);

                }
            });
            for(int i=1;i<length;i++)
            {
                radioButton = new RadioButton(MainActivity.this);
               radioButton.setText(gamesList.get(i).getGTitle()+" ,Released on : "+gamesList.get(i).getReleaseDate()+", Platform :"+gamesList.get(i).getPlatform());
                rdGrp.addView(radioButton);

            }

            linearLayout.addView(rdGrp);
       }
    }
    @Override
    public void setupData(ArrayList<GetGame> result) {
        GLArrayList=result;
        fillGameList(GLArrayList);
        progressmain.dismiss();
        goBtn.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
    }
}
