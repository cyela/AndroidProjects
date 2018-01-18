package com.example.chandrakanth.thegamesdb;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Chandrakanth on 2/17/2017.
 */

public class GamesListUtil {
    static class GamesSaxParser {
        GetGame GL;
        ArrayList<GetGame> GLList;
        static ArrayList<String> mySimilarGames=new ArrayList<String>();
        static boolean check=false;

        static ArrayList<GetGame> parseGame(InputStream in) throws XmlPullParserException, IOException {

            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in, "UTF-8");
            GetGame GL = null;
            ArrayList<GetGame> GLArrayList = new ArrayList<GetGame>();
            int event = parser.getEventType();
            while ((event) != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("Game")) {
                            GL = new GetGame();
                        }
                        if (GL != null) {
                            if (parser.getName().equals("id")) {
                                GL.setId(parser.nextText().trim());
                            }
                        }
                        if (GL != null) {
                            if (parser.getName().equals("GameTitle")) {
                                GL.setGTitle(parser.nextText().trim());
                            }
                        }
                        if (GL != null) {
                            if (parser.getName().equals("ReleaseDate")) {
                                GL.setReleaseDate(parser.nextText().trim());
                            }
                        }
                        if (GL != null) {
                            if (parser.getName().equals("Platform")) {
                                GL.setPlatform(parser.nextText().trim());

                            }
                        }
                        if(GL!=null){
                            if (parser.getName().equalsIgnoreCase("Similar")){
                                Log.d("line no",Integer.toString(parser.getLineNumber()));
                                GamesSaxParser.check = true;
                                Log.d("demo5","Count found");
                            }
                        }
                        if(parser.getName().equalsIgnoreCase("id")){
                            if(GamesSaxParser.check)
                            {
                               mySimilarGames.add(parser.nextText());

                            }
                        }


                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equalsIgnoreCase("Game")){
                            GL.setSimilarId(mySimilarGames);
                            GLArrayList.add(GL);
                            GL=null;
                        }

                        if(parser.getName().equalsIgnoreCase("Similar")){
                            if(GamesSaxParser.check) {
                                GamesSaxParser.check = false;
                            }
                        }
                    default:
                        break;


                }
                event= parser.next();
            }
            Log.d("demo",GLArrayList.toString());

                return GLArrayList;

        }
    }
}
