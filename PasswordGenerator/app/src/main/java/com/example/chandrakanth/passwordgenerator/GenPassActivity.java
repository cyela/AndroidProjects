package com.example.chandrakanth.passwordgenerator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class GenPassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gen_pass);
        ScrollView sv= (ScrollView)findViewById(R.id.ScrollView);
        ScrollView sva= (ScrollView)findViewById(R.id.ScrollViewA);
        LinearLayout LL=(LinearLayout)findViewById(R.id.LinearLayout);
        LinearLayout LLa=(LinearLayout)findViewById(R.id.LinearLayoutA);
        //int c=getIntent().getExtras().getInt("count");
        ArrayList<String> t= (ArrayList<String>) getIntent().getExtras().getStringArrayList("TP");
        ArrayList<String> as= (ArrayList<String>) getIntent().getExtras().getStringArrayList("AP");
        Button bf=(Button)findViewById(R.id.buttonFinish);
        //Toast.makeText(getApplicationContext(),a.get(0),Toast.LENGTH_LONG).show();
        TextView[] ta=new TextView[t.size()];
        for (int i=0;i<t.size();i++)
        {
            ta[i]=new TextView(this);
            ta[i].setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            ta[i].setText(t.get(i));
            LL.addView(ta[i]);
        }
        TextView[] tas=new TextView[as.size()];
        for (int i=0;i<as.size();i++)
        {
            tas[i]=new TextView(this);
            tas[i].setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            tas[i].setText(as.get(i));
            LLa.addView(tas[i]);
        }
        bf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }
}

