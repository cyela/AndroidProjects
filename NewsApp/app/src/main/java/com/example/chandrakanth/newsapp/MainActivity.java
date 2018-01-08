package com.example.chandrakanth.newsapp;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements GetNewsAsyncTask.Idata,GetImageAsyncTask.Iimage {

    Spinner spinner;
    ProgressDialog progress;
    final static String Key="0589f4b0ec5f4ae6a7a2d8c082556d0a";
    final static String Base_Url="https://newsapi.org/v1/articles";
    HashMap<String,String> News_Sources = new HashMap<String, String>(){
        {
            put("BBC","bbc-news");
            put("CNN","cnn");
            put("Buzzfeed","buzzfeed");
            put("ESPN","espn");
            put("Sky News","sky-news");
        }
    };

    ArrayList<News> news;
    int counter =0,size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.Source);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        findViewById(R.id.GetNews).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String source = spinner.getSelectedItem().toString();
                String url = Base_Url+"?apiKey="+Key+"&source="+News_Sources.get(source);

                progress = new ProgressDialog(MainActivity.this);
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setCancelable(false);
                progress.show();

                counter=0;

                new GetNewsAsyncTask(MainActivity.this).execute(url);

            }
        });


        findViewById(R.id.IBFirst).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(news!=null){
                    counter=0;
                    FillNewsDetails(news.get(counter));
                }

            }
        });
        findViewById(R.id.IBLast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(news!=null) {
                    counter = size - 1;
                    FillNewsDetails(news.get(counter));
                }

            }
        });
        findViewById(R.id.IBPrevious).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(news!=null){
                    if(counter>0) {
                        counter -= 1;
                        FillNewsDetails(news.get(counter));
                    }
                }

            }
        });
        findViewById(R.id.IBNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(news!=null){
                    if(counter<size-1) {
                        counter += 1;
                        FillNewsDetails(news.get(counter));
                    }
                }
            }
        });
        findViewById(R.id.buttonFinish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void FillNewsDetails(News news){

        Log.d("demo","fill news");

        if(news != null){
            new GetImageAsyncTask(MainActivity.this).execute(news.getUrlToImage());

            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.scrollViewLinear);
            //spinner.setBackground(getResources().getDrawable(R.drawable.spinner_border));

            linearLayout.removeAllViews();

            TextView textView = new TextView(MainActivity.this);
            textView.setText(news.getTitle());
            textView.setTextSize(15);
            textView.setTypeface(null, Typeface.BOLD);
            linearLayout.addView(textView);

            textView = new TextView(MainActivity.this);
            textView.setText("Author: "+news.getAuthor());
            linearLayout.addView(textView);

            textView = new TextView(MainActivity.this);
            textView.setText("Published on: "+news.getPublishedAt());
            linearLayout.addView(textView);

            textView = new TextView(MainActivity.this);
            textView.setText("");
            linearLayout.addView(textView);

            textView = new TextView(MainActivity.this);
            textView.setText("Description:");
            linearLayout.addView(textView);

            textView = new TextView(MainActivity.this);
            textView.setText(news.getDescription());
            linearLayout.addView(textView);

        }


    }

    @Override
    public void setupData(ArrayList<News> result) {

        news=result;
        progress.dismiss();

        size=result.size();
        FillNewsDetails(news.get(counter));
    }

    @Override
    public void setupImage(Bitmap result) {

        if(result!=null){
            ImageView newsImage = (ImageView)findViewById(R.id.imageNews);
            newsImage.setImageBitmap(result);
        }

    }
}
