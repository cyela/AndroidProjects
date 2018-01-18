package com.example.chandrakanth.thegamesdb;

import java.util.ArrayList;

/**
 * Created by Chandrakanth on 2/18/2017.
 */

public class GetGame {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;
    String GTitle;
    String Overview;
    String Genre;
    String Publisher;
    String BaseUrlImage;
    String youtube;

    public String getPlatform() {
        return Platform;
    }

    public void setPlatform(String platform) {
        Platform = platform;
    }

    String Platform;

    public String getReleaseDate() {
        return ReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        ReleaseDate = releaseDate;
    }

    String ReleaseDate;

    public ArrayList<String> getSimilarId() {
        return SimilarId;
    }

    public void setSimilarId(ArrayList<String> similarId) {
        SimilarId = similarId;
    }

    ArrayList<String> SimilarId;

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }



    public String getBaseUrlImage() {
        return BaseUrlImage;
    }

    public void setBaseUrlImage(String baseUrlImage) {
        BaseUrlImage = baseUrlImage;
    }



    public String getGTitle() {
        return GTitle;
    }

    public void setGTitle(String GTitle) {
        this.GTitle = GTitle;
    }

    public String getOverview() {
        return Overview;
    }

    public void setOverview(String overview) {
        Overview = overview;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    @Override
    public String toString() {
        return "GetGame{" +
                "id='" + id + '\'' +
                ", GTitle='" + GTitle + '\'' +
                ", Overview='" + Overview + '\'' +
                ", Genre='" + Genre + '\'' +
                ", Publisher='" + Publisher + '\'' +
                ", BaseUrlImage='" + BaseUrlImage + '\'' +
                ", youtube='" + youtube + '\'' +
                ", Platform='" + Platform + '\'' +
                ", ReleaseDate='" + ReleaseDate + '\'' +
                ", SimilarId=" + SimilarId +
                '}';
    }
}
