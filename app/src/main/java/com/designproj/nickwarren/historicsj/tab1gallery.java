package com.designproj.nickwarren.historicsj;

import android.app.Activity;
import android.app.ListFragment;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StyleableRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;


import android.app.ListActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by nickwarren on 2017-03-02.
 *
 * Recycler view: http://www.androidauthority.com/how-to-build-an-image-gallery-app-718976/
 */

public class tab1gallery extends Fragment //implements MyAdapter.AdapterCallback
 {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


     // This method will be called when a com.designproj.nickwarren.historicsj.MessageEvent is posted (in the UI thread for Toast)
     @Subscribe(threadMode = ThreadMode.MAIN)
     public void onMessageEvent(PhotoObject event) {
         Toast.makeText(getActivity(), "Button pressed!", Toast.LENGTH_SHORT).show();
     }

     @Override
     public void onStart() {
         super.onStart();
         if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
     }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2gallery, container, false);

        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.imagegallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<CreateList> createLists = prepareData();
        MyAdapter adapter = new MyAdapter(getContext(), createLists);
        recyclerView.setAdapter(adapter);

    return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private ArrayList<CreateList> prepareData(){  //http://stackoverflow.com/questions/4326037/android-resource-array-of-arrays
        ArrayList<CreateList> theimage = new ArrayList<>();
        @StyleableRes int one = 1;

        Resources resources = getResources();
        TypedArray photos = resources.obtainTypedArray(R.array.historic_photo_info);

        String[][] array =  new String[photos.length()][];

        for (int i = 0; i < photos.length(); ++i) {
            int resId = photos.getResourceId(i, 0);
            if (resId > 0) {
                CreateList createList = new CreateList();

                TypedArray photoobj = resources.obtainTypedArray(resId); //2nd layer array of Drawable(index 1), string array(index 0)
                int stringsID = photoobj.getResourceId(0, 0);
                int drawable = photoobj.getResourceId(one, 0);

                if (stringsID > 0){
                    array[i] = resources.getStringArray(stringsID);
                    createList.setCaption(array[i][0]);
                    createList.setDate(array[i][1]);
                    createList.setSource(array[i][2]);
                    createList.setLatitude(Double.parseDouble(array[i][3]));
                    createList.setLongitude(Double.parseDouble(array[i][4]));
                }
                createList.setImage_ID(drawable);
                theimage.add(createList);
            }


        }
        photos.recycle();

        return theimage;

    }

}
