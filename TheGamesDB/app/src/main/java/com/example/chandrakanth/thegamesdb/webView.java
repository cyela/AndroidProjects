package com.example.chandrakanth.thegamesdb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class webView extends AppCompatActivity {
    WebView web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        String weburl = getIntent().getExtras().getString("urlforweb");
        Log.d("demo5",weburl);

        web = (WebView) findViewById(R.id.WebViewTrailer);

        WebSettings webSettings = web.getSettings();
        web.setWebViewClient(new WebViewClient());
        webSettings.setJavaScriptEnabled(true);
        web.loadUrl(weburl);






    }
}
