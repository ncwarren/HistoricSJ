package com.designproj.nickwarren.historicsj;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

/**
 * Created by nickwarren on 2017-03-04.
 */

public class CreateList {

    private Integer image_id;

    private String caption;
    private String date;
    private String source;

    private double latitude = 47.5780161;
    private double longitude = -52.7354376;

    public CreateList() {

    }

    public double getLongitude() { return longitude; }

    public void setLongitude(double longitude) {this.longitude = longitude;}

    public double getLatitude() {return latitude;}

    public void setLatitude(double latitude) {this.latitude = latitude;}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getImage_ID() {
        return image_id;
    }

    public void setImage_ID(Integer android_image_url) {
        this.image_id = android_image_url;
    }



}
