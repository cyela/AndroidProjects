package com.example.chandrakanth.thegamesdb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Chandrakanth on 2/16/2017.
 */

public class getGameImageAsync extends AsyncTask<String,Void,Bitmap> {
    Iimage activity;
    public getGameImageAsync(Iimage activity) {
        this.activity = activity;
    }



    @Override
    protected Bitmap doInBackground(String... params) {


        InputStream in;
        try {
            URL url = new URL(params[0]);
            //URL is passed through params
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            in = con.getInputStream();

            Bitmap image = BitmapFactory.decodeStream(in);
            return image;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        activity.setupImage(bitmap);
        super.onPostExecute(bitmap);
    }

    static public interface Iimage{
        public void setupImage(Bitmap bitmap);

    }





}