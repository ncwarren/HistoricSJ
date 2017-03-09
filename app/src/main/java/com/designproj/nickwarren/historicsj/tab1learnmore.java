package com.designproj.nickwarren.historicsj;

/**
 * Created by nickwarren on 2017-03-02.
 */

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Xml;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.*;
import java.io.*;


public class tab1learnmore extends Fragment {

    TextView t;
    ImageView im;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // -- inflate the layout for this fragment
        View myInflatedView = inflater.inflate(R.layout.tab1learnmore, container,false);

        // Set the Text to try this out
        this.t = (TextView) myInflatedView.findViewById(R.id.caption);

        Resources res = getResources();

        TypedArray infoDB = res.obtainTypedArray(R.array.historic_photo_info); //http://stackoverflow.com/questions/4326037/android-resource-array-of-arrays
        int numberOfPhotos = infoDB.length();
        String[][] infoDB_stringified = new String[numberOfPhotos][];
        for(int i = 0; i<numberOfPhotos; i++){
            int id = infoDB.getResourceId(i,0);
            if (id > 0){
                infoDB_stringified[i] = res.getStringArray(id);
            }
        }
        infoDB.recycle();

        t.setText("All images used under Creative Commons Non-Commercial No Derivatives v2.5 license");

        this.im = (ImageView) myInflatedView.findViewById(R.id.imageView);
        this.im.setImageResource(R.drawable.testimage);

        return myInflatedView;
    }


    // This method will be called when a com.designproj.nickwarren.historicsj.MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PhotoObject event) {
        t.setText(event.caption);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

}
