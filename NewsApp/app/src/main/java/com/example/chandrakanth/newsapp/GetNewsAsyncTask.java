package com.example.chandrakanth.newsapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Chandrakanth on 2/6/2017.
 */

public class GetNewsAsyncTask  extends AsyncTask<String, Void, ArrayList<News>> {

    Idata activity;

    public GetNewsAsyncTask(Idata activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<News> doInBackground(String... params) {

        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            int statusCode = con.getResponseCode();

            if (statusCode == HttpURLConnection.HTTP_OK) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();

                while (line != null) {
                    sb.append(line);
                    line = reader.readLine();
                }

                Log.d("demo", "get successfull");

                return JSONNews(sb.toString());
            }
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(ProtocolException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<News> result) {
        activity.setupData(result);
        super.onPostExecute(result);
    }

    public ArrayList<News> JSONNews(String in) throws JSONException {

        ArrayList<News> newsList = new ArrayList<News>();

        JSONObject parentObject = new JSONObject(in);
        JSONArray parentArray = parentObject.getJSONArray("articles");

        for (int i = 0; i < parentArray.length(); i++) {
            JSONObject finalObject = parentArray.getJSONObject(i);
            News news = new News();

            news.setAuthor(finalObject.getString("author"));
            news.setDescription(finalObject.getString("title"));
            news.setTitle(finalObject.getString("description"));
            news.setUrlToImage(finalObject.getString("urlToImage"));
            news.setPublishedAt(finalObject.getString("publishedAt"));

            newsList.add(news);
        }
        return newsList;
    }

    static public interface Idata{
        public void setupData(ArrayList<News> result);
    }

}

