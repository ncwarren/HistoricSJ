package com.designproj.nickwarren.historicsj;

import android.app.Activity;
import android.app.ListFragment;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class tab2gallery extends Fragment //implements MyAdapter.AdapterCallback
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

    private ArrayList<CreateList> prepareData(){

        ArrayList<CreateList> theimage = new ArrayList<>();
        for(int i = 0; i< image_titles.length; i++){
            CreateList createList = new CreateList();
            createList.setImage_title(image_titles[i]);
            createList.setImage_ID(image_ids[i]);
            theimage.add(createList);
        }
        return theimage;
    }

    private final String image_titles[] = {
            "Img1",
            "Img2",
            "Img3",
            "Img4",
            "Img5",
            "Img6",
            "Img7",
            "Img8",
            "Img9",
            "Img10",
            "Img11",
            "Img12",
            "Img13",
    };

    private final Integer image_ids[] = {
            R.drawable.photo1,
            R.drawable.testimage,
            R.drawable.testimage,
            R.drawable.testimage,
            R.drawable.testimage,
            R.drawable.testimage,
            R.drawable.testimage,
            R.drawable.testimage,
            R.drawable.testimage,
            R.drawable.testimage,
            R.drawable.testimage,
            R.drawable.testimage,
            R.drawable.testimage,
    };
}
