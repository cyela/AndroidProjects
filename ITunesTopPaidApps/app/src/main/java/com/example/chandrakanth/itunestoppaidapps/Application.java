package com.example.chandrakanth.itunestoppaidapps;

import java.io.Serializable;

/**
 * Created by Chandrakanth on 2/22/2017.
 */

public class Application implements Serializable{

    @Override
    public String toString() {
        return "Application{" +
                "AppPriceCurrency='" + AppPriceCurrency + '\'' +
                ", AppPrice=" + AppPrice +
                ", AppName='" + AppName + '\'' +
                ", AppImageUrl='" + AppImageUrl + '\'' +
                '}';
    }

    public Float getAppPrice() {
        return AppPrice;
    }

    public void setAppPrice(Float appPrice) {
        AppPrice = appPrice;
    }

    public String getAppPriceCurrency() {
        return AppPriceCurrency;
    }

    public void setAppPriceCurrency(String appPriceCurrency) {
        AppPriceCurrency = appPriceCurrency;
    }

    String AppPriceCurrency;
    Float AppPrice;
    public String getAppName() {
        return AppName;
    }

    public void setAppName(String appName) {
        AppName = appName;
    }

    String AppName;

    public String getAppImageUrl() {
        return AppImageUrl;
    }

    public void setAppImageUrl(String appImageUrl) {
        AppImageUrl = appImageUrl;
    }

    String AppImageUrl;
}
