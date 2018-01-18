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
 * Created by Chandrakanth on 2/18/2017.
 */

public class getGameAsync extends AsyncTask<String, Object, ArrayList<GetGame>> {
    Idata2 activity;

    public getGameAsync(Idata2 activity) {
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
                return getGameUtil.getGamePullParser.parseGetGame(in);

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





    static public interface Idata2{
        public void setupData(ArrayList<GetGame> result);
    }

}
