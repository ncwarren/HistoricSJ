package com.designproj.nickwarren.historicsj;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by nickwarren on 2017-03-02.
 */

public class tab3mapsplaceholder extends Fragment implements OnMapReadyCallback {



    private GoogleMap mMap;

    public static tab3mapsplaceholder newInstance() {
        tab3mapsplaceholder fragment = new tab3mapsplaceholder();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gmaps, null, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng marker = new LatLng(47.5744, -52.735);       //creating coordinate object
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 13));        //center camera to marker
        googleMap.addMarker(new MarkerOptions().title("Location").position(marker));        //add pin and location name
    }
}