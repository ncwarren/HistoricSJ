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

import java.util.ArrayList;

/**
 * Created by nickwarren on 2017-03-02.
 *
 * Recycler view: http://www.androidauthority.com/how-to-build-an-image-gallery-app-718976/
 */

public class tab2gallery extends Fragment implements AdapterView.OnClickListener{
    OnPhotoSelectedListener mCallback;
    Activity activity;
    //ListView list;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        //this.setListAdapter(new ArrayAdapter<String>(
        //        this, R.layout.mylist,
         //       R.id.Itemname,itemname));

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(), "Item", Toast.LENGTH_LONG).show();
    }

    public interface OnPhotoSelectedListener{
        public void onPhotoSelected(int position);
    }

    public void OnAttach(AppCompatActivity activity){
        super.onAttach(activity);
        this.activity = activity;

        try{
            mCallback = (OnPhotoSelectedListener) activity;

        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + " must implement OnPhotoSlectedListener");
        }
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

        //String[][] infoDB = GetlistContact();
        //ListView lv = (ListView)rootView.findViewById(R.id.list);
        //lv.setAdapter(new ListViewGalleryAdapter(getActivity(), infoDB));

    return rootView;
    }


    private String[][] GetlistContact(){

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

        return infoDB_stringified;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //ArrayAdapter appter = ArrayAdapter.createFromResource(getActivity(), R.array.photo2696, android.R.layout.simple_list_item_1);
        //setListAdapter(appter);
        //getListView().setOnItemClickListener(this);

        //list = (ListView) findViewById(R.id.list);
    }

    //@Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
        Toast.makeText(getActivity(), "Item" + i, Toast.LENGTH_LONG).show();
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
            R.drawable.testimage,
    };
}
