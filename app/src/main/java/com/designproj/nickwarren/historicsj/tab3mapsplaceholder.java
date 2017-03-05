package com.designproj.nickwarren.historicsj;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by nickwarren on 2017-03-02.
 */

public class tab3mapsplaceholder extends Fragment implements OnMapReadyCallback {
    private View rootView;
    private SupportMapFragment mapFragment = new SupportMapFragment();
    String TAG = "Log";


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            rootView = inflater.inflate(R.layout.fragment_gmaps, null, false);

            mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment.onCreate(savedInstanceState);
            mapFragment.getMapAsync(this);
        }
        catch(InflateException e){
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
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState); mapFragment.onSaveInstanceState(outState);
    }
    @Override
    public void onLowMemory()
    {
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
        LatLng marker = new LatLng(47.5744, -52.735);       //creating coordinate object
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 13));        //center camera to marker
        googleMap.addMarker(new MarkerOptions().title("Location").position(marker));        //add pin and location name
    }
}