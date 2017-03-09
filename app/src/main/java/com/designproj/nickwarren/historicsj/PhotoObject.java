package com.designproj.nickwarren.historicsj;

/**
 * Created by nickwarren on 2017-03-06.
 */

public class PhotoObject {
    public String caption;
    public String date;
    public int idname;
    public String source;

    public float latitude;
    public float longitude;



    public PhotoObject(int idname, String caption, String date, String source, float latitude, float longitude){
        this.caption = caption;
        this.date = date;
        this.idname = idname;
        this.source = source;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
