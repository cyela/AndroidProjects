package com.example.chandrakanth.itunestoppaidapps;

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
 * Created by Chandrakanth on 2/22/2017.
 */

public class GetApplicationAsyncTask extends AsyncTask<String,Void,ArrayList<Application>> {
    public GetApplicationAsyncTask(IData activity) {
        this.activity = activity;
    }

    IData activity;
    @Override
    protected void onPostExecute(ArrayList<Application> applications) {
        Log.d("demoRes","yes");
        activity.setupData(applications);
        super.onPostExecute(applications);
    }

    @Override
    protected ArrayList<Application> doInBackground(String... params) {
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
                return JSONApplication(sb.toString());
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
     private ArrayList<Application> JSONApplication(String in) throws JSONException{
         ArrayList<Application> AppList=new ArrayList<Application>();
         Application application;

         try{
             JSONObject root=new JSONObject(in);
             if(root.has("feed"))
             {
                 JSONObject rootFeed = root.getJSONObject("feed");
                 Log.d("demo12", rootFeed.toString());
                 if(rootFeed.has("entry")){
                     Log.d("demo123","hello");
                     JSONArray rootEntry=rootFeed.getJSONArray("entry");
                     Log.d("demo1234", String.valueOf(rootEntry.length()));

                     for(int i=0;i<rootEntry.length();i++)
                     {
                         application=new Application();
                        JSONObject Entry=rootEntry.getJSONObject(i);
                         if(Entry.has("im:name"))
                         {

                            JSONObject name=Entry.getJSONObject("im:name");

                             if(name.has("label"))
                             {
                                 application.setAppName(name.getString("label"));

                             }


                         }
                         if(Entry.has("im:image"))
                         {

                             JSONArray imageArray=Entry.getJSONArray("im:image");
                             JSONObject imageobject=imageArray.getJSONObject(2);

                                 if(imageobject.has("label"))
                                 {
                                     application.setAppImageUrl(imageobject.getString("label"));

                                 }

                         }
                         if(Entry.has("im:price"))
                         {

                          JSONObject priceObject=Entry.getJSONObject("im:price");
                             if(priceObject.has("attributes")){
                                 JSONObject attributeObject=priceObject.getJSONObject("attributes");
                                 if(attributeObject.has("amount")) {
                                     application.setAppPrice(Float.valueOf(attributeObject.getString("amount")));

                                 }
                                 if(attributeObject.has("currency"))
                                 {
                                     application.setAppPriceCurrency((attributeObject.getString("currency")));
                                     Log.d("demo",application.toString());
                                 }
                             }
                         }
                         AppList.add(application);
                     }
                 }
            }
             return AppList;
            }

         catch (JSONException e)
         {
             e.printStackTrace();
         }
return null;

     }
    static public interface IData{
        public void setupData(ArrayList<Application> result);
    }


}
