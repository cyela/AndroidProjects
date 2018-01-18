package com.example.chandrakanth.thegamesdb;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Chandrakanth on 2/19/2017.
 */

public class getGameUtil {
    static class getGamePullParser {
        GetGame Gg;
        ArrayList<GetGame> GgList;
        static boolean check=false;
        static ArrayList<String> mySimilarGames=new ArrayList<String>();

        static ArrayList<GetGame> parseGetGame(InputStream in) throws XmlPullParserException, IOException {

            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in, "UTF-8");
            GetGame Gg = null;
            ArrayList<GetGame> GgList = new ArrayList<GetGame>();
            int event = parser.getEventType();
            while ((event) != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("Data")) {
                            Gg = new GetGame();
                        }
                        if(Gg!=null) {
                            if (parser.getName().equals("GameTitle")) {
                                Gg.setGTitle(parser.nextText().trim());
                            }
                        }

                        if(Gg != null) {
                        if (parser.getName().equals("Publisher")) {
                                Gg.setPublisher(parser.nextText().trim());
                            }
                        }
                        if(Gg != null) {
                        if (parser.getName().equals("Overview")) {
                                Gg.setOverview(parser.nextText().trim());
                        }
                        }
                        if(Gg != null) {
                            if (parser.getName().equals("genre")) {
                                Gg.setGenre(parser.nextText().trim());
                            }
                        }

                        if(Gg != null) {
                        if (parser.getName().equals("thumb")) {
                                Gg.setBaseUrlImage(parser.nextText().trim());
                            }
                        }
                        if(Gg != null) {
                            if (parser.getName().equals("Images")) {
                                Gg.setBaseUrlImage(parser.getAttributeValue(null,"thumb"));
                            }
                        }
                        if(Gg != null) {
                            if (parser.getName().equals("Youtube")) {
                                Gg.setYoutube(parser.nextText().trim());
                            }
                        }
                        if(Gg!=null){
                            if (parser.getName().equalsIgnoreCase("Similar")){

                                getGamePullParser.check = true;

                            }
                        }
                        if(parser.getName().equalsIgnoreCase("id")){
                            if(getGamePullParser.check)
                            {
                                mySimilarGames.add(parser.nextText());

                            }
                        }

                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("Data")) {
                            Gg.setSimilarId(mySimilarGames);
                            GgList.add(Gg);
                            Gg = null;
                        }


                    default:
                        break;


                }
                event= parser.next();
            }
            Log.d("demo2",GgList.toString());
            return GgList;

        }
    }
}
