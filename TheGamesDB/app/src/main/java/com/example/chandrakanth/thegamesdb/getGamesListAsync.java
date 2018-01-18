package com.example.chandrakanth.thegamesdb;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Chandrakanth on 2/17/2017.
 */

public class getGamesListAsync extends AsyncTask<String,Void,ArrayList<GetGame>> {
Idata activity;

    public getGamesListAsync(Idata activity) {
        this.activity = activity;
    }

    @Override
    protected void onPostExecute(ArrayList<GetGame> result) {
        activity.setupData(result);
        super.onPostExecute(result);
    }

    @Override
    protected ArrayList<GetGame> doInBackground(String... params) {

        URL url= null;
        try {
            url = new URL(params[0]);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode=con.getResponseCode();
            if(statusCode==HttpURLConnection.HTTP_OK) {
                InputStream in = con.getInputStream();
                return GamesListUtil.GamesSaxParser.parseGame(in);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return null;
    }





    static public interface Idata{
        public void setupData(ArrayList<GetGame> result);
    }

}

