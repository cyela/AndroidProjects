package com.example.chandrakanth.thegamesdb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class GameDetailActivity extends AppCompatActivity implements getGameAsync.Idata2, getGameImageAsync.Iimage {
    ProgressDialog progress;
    ArrayList<GetGame> resltGetGame;
    TextView title,overView,genre,publisher;
    ImageView imgView;
    Button btnTr;
    Handler handler;
    Intent intentT;
    Intent intentS;
    String BaseImgUrl="http://thegamesdb.net/banners/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        progress =new ProgressDialog(this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.show();
        String gameUrlValue=getIntent().getExtras().getString("url");

        new getGameAsync(this).execute(gameUrlValue);
        Log.d("demo",gameUrlValue);
        btnTr=(Button)findViewById(R.id.PTrailer);
        Button btnSg=(Button)findViewById(R.id.SGames);
        Button btnFnsh=(Button)findViewById(R.id.btnFinish);
        btnTr.setBackgroundColor(ContextCompat.getColor(GameDetailActivity.this, R.color.colorPrimary));
        btnSg.setBackgroundColor(ContextCompat.getColor(GameDetailActivity.this, R.color.colorPrimary));
        btnFnsh.setBackgroundColor(ContextCompat.getColor(GameDetailActivity.this, R.color.colorPrimary));
        title = (TextView)findViewById(R.id.TitleTxtView);
        overView = (TextView)findViewById(R.id.OverViewTextView1);
        genre = (TextView)findViewById(R.id.GenreTextView);
        publisher = (TextView)findViewById(R.id.PublisherTextView);
        btnFnsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ArrayList<String> IdSet=intentS.getExtras().getStringArrayList("IdSet");
                String key=intentS.getExtras().getString("SearchGame");
                String Title=intentS.getExtras().getString("GameTitle");
                Intent intentSg=new Intent(GameDetailActivity.this,SimilarGameActivity.class);
                intentSg.putExtra("idSet",IdSet);
                intentSg.putExtra("Key",key);
                intentSg.putExtra("Title",Title);
                startActivity(intentSg);

               IdSet.clear();

            }

        });

        btnTr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent WVintent = new Intent(GameDetailActivity.this,webView.class);
               String urlweb = intentT.getExtras().getString("urlforintentT");
                if(urlweb!="nourl") {
                    Log.d("demoo", urlweb);
                    WVintent.putExtra("urlforweb",urlweb);
                    startActivity(WVintent);

                }
                else{
                    Toast.makeText(GameDetailActivity.this,"No Trailer to show",Toast.LENGTH_LONG).show();
                }

            }
        });



    }
    private void fillInGameDetail(final GetGame getGame) {
        if(getGame!=null) {
                    new getGameImageAsync(GameDetailActivity.this).execute(BaseImgUrl + getGame.getBaseUrlImage());
                    title.setText(getGame.getGTitle());
                    title.setTypeface(null, Typeface.BOLD);

                    overView.setText(getGame.getOverview());
                    overView.setTypeface(null, Typeface.BOLD);
                    overView.setMovementMethod(new ScrollingMovementMethod());
                    genre.setText("Genre : " + getGame.getGenre());
                    genre.setTypeface(null, Typeface.BOLD);
                    publisher.setText("publisher  : " + getGame.getPublisher());
                    publisher.setTypeface(null, Typeface.BOLD);
                    imgView=(ImageView)findViewById(R.id.imageView);
                    intentT = new Intent();
                    if (getGame.getYoutube() != null) {

                        intentT.putExtra("urlforintentT", getGame.getYoutube());

                    } else {
                        intentT.putExtra("urlforintentT", "nourl");
                    }
            intentS=new Intent();
            if(getGame.getSimilarId()!=null)
            {
                intentS.putExtra("IdSet",getGame.getSimilarId());
                intentS.putExtra("GameTitle",getGame.getGTitle());
                intentS.putExtra("SearchGame",getIntent().getExtras().getString("SearchedGame"));

            }
            progress.dismiss();
        }
    }

    @Override
    public void setupData(ArrayList<GetGame> result) {
        if(result!=null) {
            resltGetGame = result;

            fillInGameDetail(resltGetGame.get(0));
        }
    }

    @Override
    public void setupImage(Bitmap bitmap) {
        if(bitmap!=null) {
            imgView.setImageBitmap(bitmap);

        }

    }
}






