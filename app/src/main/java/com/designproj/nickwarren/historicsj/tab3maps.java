package com.designproj.nickwarren.historicsj;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleableRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by nickwarren on 2017-03-02.
 *
 *
 * https://developers.google.com/maps/documentation/android-api/marker
 *
 * https://code.tutsplus.com/tutorials/how-to-work-with-geofences-on-android--cms-26639
 *
 */

public class tab3maps extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {
    private View rootView;
    private SupportMapFragment mapFragment = new SupportMapFragment();
    String TAG = "Log";
    LatLng marker;
    GoogleMap mapObject;
    ArrayList<CreateList> galleryList;

    private final int[] MAP_TYPES = {GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE};


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try {
            rootView = inflater.inflate(R.layout.fragment_gmaps, null, false);

            mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment.onCreate(savedInstanceState);
            mapFragment.getMapAsync(this);
        } catch (InflateException e) {
            Log.e(TAG, "Inflate exception");
        }
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        mapFragment.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapFragment.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapFragment.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapFragment.onLowMemory();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapFragment.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //add pin and location name
        mapObject = googleMap;
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
                //int drawable = photoobj.getResourceId(one, 0);

                if (stringsID > 0){
                    array[i] = resources.getStringArray(stringsID);
                    array[i] = resources.getStringArray(stringsID);
                    createList.setCaption(array[i][0]);
                    createList.setDate(array[i][1]);
                    createList.setSource(array[i][2]);
                    createList.setLatitude(Double.parseDouble(array[i][3]));
                    createList.setLongitude(Double.parseDouble(array[i][4]));

                    marker = new LatLng(Double.parseDouble(array[i][3]), Double.parseDouble(array[i][4])); //creating coordinate object
                    mapObject.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 13));        //center camera to marker
                    mapObject.addMarker(new MarkerOptions().title("").position(marker));
                }
                createList.setImage_ID(photoobj.getResourceId(one, 0));
                theimage.add(createList);

            }

        }

        galleryList = theimage;
        mapObject.setOnMarkerClickListener(this);
    }


    // This method will be called when a com.designproj.nickwarren.historicsj.MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PhotoObject event) {
        marker = new LatLng(event.latitude, event.longitude); //creating coordinate object
        mapObject.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 18));        //center camera to marker
        mapObject.addMarker(new MarkerOptions().title(event.caption).position(marker));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        //marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        int position = 0;

        for (int i = 0; i < galleryList.size(); i++){
            if (galleryList.get(i).getLatitude() == marker.getPosition().latitude && galleryList.get(i).getLongitude() == marker.getPosition().longitude){
                position = i;
            }
        }

        EventBus.getDefault().post(new PhotoObject(galleryList.get(position).getImage_ID(),
                galleryList.get(position).getCaption(),
                galleryList.get(position).getDate(),
                galleryList.get(position).getSource(),
                galleryList.get(position).getLatitude(),
                galleryList.get(position).getLongitude()));



        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }


}

