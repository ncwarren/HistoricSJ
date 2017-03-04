package com.designproj.nickwarren.historicsj;

/**
 * Created by nickwarren on 2017-03-02.
 */

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.media.Image;
import android.os.Bundle;
import android.util.Xml;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.*;
import java.io.*;


public class tab1learnmore extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // -- inflate the layout for this fragment
        View myInflatedView = inflater.inflate(R.layout.tab1learnmore, container,false);

        // Set the Text to try this out
        TextView t = (TextView) myInflatedView.findViewById(R.id.caption);

        Resources res = getResources();

        TypedArray infoDB = res.obtainTypedArray(R.array.historic_photo_info); //http://stackoverflow.com/questions/4326037/android-resource-array-of-arrays
        int numberOfPhotos = infoDB.length();
        String[][] infoDB_stringified = new String[numberOfPhotos][];
        for(int i = 0; i<numberOfPhotos; i++){
            int id = infoDB.getResourceId(i,0);
            if (id > 0){
                infoDB_stringified[i] = res.getStringArray(id);
            } else{

            }
        }
        infoDB.recycle();

        t.setText(infoDB_stringified[0][1]);

        ImageView im = (ImageView) myInflatedView.findViewById(R.id.imageView);
        im.setImageResource(R.drawable.testimage);



        return myInflatedView;
    }




    public void setText(String text){
        TextView Caption = (TextView) getActivity().findViewById(R.id.caption); //getView() is v importmant as findViewByID is provided in Activity class, not Fragment class
        //ImageView Viewer = (ImageView) getView().findViewById(R.id);
        Caption.setText("sample caption");
    }
}
