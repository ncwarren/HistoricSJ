package com.designproj.nickwarren.historicsj;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleableRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import static com.designproj.nickwarren.historicsj.R.id.map;

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
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapClickListener
    {

    private static final int REQ_PERMISSION = 1;
    private View rootView;
    private SupportMapFragment mapFragment = new SupportMapFragment();
    String TAG = "Log";
    LatLng marker;
    GoogleMap mapObject;
    ArrayList<CreateList> galleryList;
    GoogleApiClient googleApiClient;
    private Location lastLocation;


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

            mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(map);
            mapFragment.onCreate(savedInstanceState);
            mapFragment.getMapAsync(this);
        } catch (InflateException e) {
            Log.e(TAG, "Inflate exception");
        }
        createGoogleApi();


        return rootView;
    }

    private void createGoogleApi() {
        Log.d(TAG, "createGoogleApi()");
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        // Disconnect GoogleApiClient when stopping Activity
        googleApiClient.disconnect();
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
        String[][] array = new String[photos.length()][];

        for (int i = 0; i < photos.length(); ++i) {
            int resId = photos.getResourceId(i, 0);
            if (resId > 0) {
                CreateList createList = new CreateList();

                TypedArray photoobj = resources.obtainTypedArray(resId); //2nd layer array of Drawable(index 1), string array(index 0)
                int stringsID = photoobj.getResourceId(0, 0);
                //int drawable = photoobj.getResourceId(one, 0);

                if (stringsID > 0) {
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
        mapObject.setOnMapClickListener(this);
        startGeofence();
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
        // Call GoogleApiClient connection when starting the Activity
        googleApiClient.connect();

        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Log.d(TAG, "onMapClick("+latLng +")");
        markerForGeofence(latLng);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        //marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        int position = 0;

        for (int i = 0; i < galleryList.size(); i++) {
            if (galleryList.get(i).getLatitude() == marker.getPosition().latitude && galleryList.get(i).getLongitude() == marker.getPosition().longitude) {
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


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getLastKnownLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    // Get last known location
    private void getLastKnownLocation() {
        Log.d(TAG, "getLastKnownLocation()");
        if (checkPermission()) {
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (lastLocation != null) {
                Log.i(TAG, "LasKnown location. " +
                        "Long: " + lastLocation.getLongitude() +
                        " | Lat: " + lastLocation.getLatitude());
                writeLastLocation();
                startLocationUpdates();
            } else {
                Log.w(TAG, "No location retrieved yet");
                startLocationUpdates();
            }
        } else askPermission();
    }

    private LocationRequest locationRequest;
    // Defined in mili seconds.
    // This number in extremely low, and should be used only for debug
    private final int UPDATE_INTERVAL = 1000;
    private final int FASTEST_INTERVAL = 900;

    // Start location Updates
    private void startLocationUpdates() {
        Log.i(TAG, "startLocationUpdates()");
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);

        if (checkPermission())
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, (LocationListener) this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged [" + location + "]");
        lastLocation = location;
        writeActualLocation(location);
    }

    // Write location coordinates on UI
    private void writeActualLocation(Location location) {
        //textLat.setText( "Lat: " + location.getLatitude() );
        //textLong.setText( "Long: " + location.getLongitude() );
        markerLocation(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    private void writeLastLocation() {
        writeActualLocation(lastLocation);
    }

    // Check for permission to access Location
    private boolean checkPermission() {
        Log.d(TAG, "checkPermission()");
        // Ask for permission if it wasn't granted yet
        return (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED);
    }

    // Asks for permission
    private void askPermission() {
        Log.d(TAG, "askPermission()");
        ActivityCompat.requestPermissions(
                getActivity(), //here
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                REQ_PERMISSION
        );
    }

    // Verify user's response of the permission requested
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult()");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                    getLastKnownLocation();

                } else {
                    // Permission denied
                    permissionsDenied();
                }
                break;
            }
        }
    }

    // App cannot work without the permissions
    private void permissionsDenied() {
        Log.w(TAG, "permissionsDenied()");
    }


    private Marker locationMarker;

    // Create a Location Marker
    private void markerLocation(LatLng latLng) {
        Log.i(TAG, "markerLocation(" + latLng + ")");
        String title = latLng.latitude + ", " + latLng.longitude;
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title(title);
        if (mapObject != null) {
            // Remove the anterior marker
            if (locationMarker != null)
                locationMarker.remove();
            locationMarker = mapObject.addMarker(markerOptions);
            float zoom = 14f;
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
            mapObject.animateCamera(cameraUpdate);
        }
    }

    private Marker geoFenceMarker;

    // Create a marker for the geofence creation
    private void markerForGeofence(LatLng latLng) {
        Log.i(TAG, "markerForGeofence(" + latLng + ")");
        String title = latLng.latitude + ", " + latLng.longitude;
        // Define marker options
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                .title(title);
        if (mapObject != null) {
            // Remove last geoFenceMarker
            if (geoFenceMarker != null)
                geoFenceMarker.remove();

            geoFenceMarker = mapObject.addMarker(markerOptions);
        }
    }


        private static final long GEO_DURATION = 60 * 60 * 1000;
        private static final String GEOFENCE_REQ_ID = "My Geofence";
        private static final float GEOFENCE_RADIUS = 500.0f; // in meters

        // Create a Geofence
        private Geofence createGeofence(LatLng latLng, float radius ) {
            Log.d(TAG, "createGeofence");
            return new Geofence.Builder()
                    .setRequestId(GEOFENCE_REQ_ID)
                    .setCircularRegion( latLng.latitude, latLng.longitude, radius)
                    .setExpirationDuration( GEO_DURATION )
                    .setTransitionTypes( Geofence.GEOFENCE_TRANSITION_ENTER
                            | Geofence.GEOFENCE_TRANSITION_EXIT )
                    .build();
        }

        // Create a Geofence Request
        private GeofencingRequest createGeofenceRequest( Geofence geofence ) {
            Log.d(TAG, "createGeofenceRequest");
            return new GeofencingRequest.Builder()
                    .setInitialTrigger( GeofencingRequest.INITIAL_TRIGGER_ENTER )
                    .addGeofence( geofence )
                    .build();
        }

        private PendingIntent geoFencePendingIntent;
        private final int GEOFENCE_REQ_CODE = 0;
        private PendingIntent createGeofencePendingIntent() {
            Log.d(TAG, "createGeofencePendingIntent");
            if ( geoFencePendingIntent != null )
                return geoFencePendingIntent;

            Intent intent = new Intent( getContext(), GeofenceTrasitionService.class);
            return PendingIntent.getService(
                    getContext(), GEOFENCE_REQ_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT );
        }

        // Add the created GeofenceRequest to the device's monitoring list
        private void addGeofence(GeofencingRequest request) {
            Log.d(TAG, "addGeofence");
            if (checkPermission())
                LocationServices.GeofencingApi.addGeofences(
                        googleApiClient,
                        request,
                        createGeofencePendingIntent()
                ).setResultCallback((ResultCallback<? super Status>) this);
        }

        //@Override
        public void onResult(@NonNull Status status) {
            Log.i(TAG, "onResult: " + status);
            if ( status.isSuccess() ) {
                drawGeofence();
            } else {
                // inform about fail
            }
        }

        // Draw Geofence circle on GoogleMap
        private Circle geoFenceLimits;
        private void drawGeofence() {
            Log.d(TAG, "drawGeofence()");

            if ( geoFenceLimits != null )
                geoFenceLimits.remove();

            CircleOptions circleOptions = new CircleOptions()
                    .center( geoFenceMarker.getPosition())
                    .strokeColor(Color.argb(50, 70,70,70))
                    .fillColor( Color.argb(100, 150,150,150) )
                    .radius( GEOFENCE_RADIUS );
            geoFenceLimits = mapObject.addCircle( circleOptions );
        }


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch ( item.getItemId() ) {
                //case R.id.geofence: {
                //    startGeofence();
                  //  return true;
                //}
            }
            return super.onOptionsItemSelected(item);
        }

        // Start Geofence creation process
        private void startGeofence() {
            Log.i(TAG, "startGeofence()");
            if( geoFenceMarker != null ) {
                Geofence geofence = createGeofence( geoFenceMarker.getPosition(), GEOFENCE_RADIUS );
                GeofencingRequest geofenceRequest = createGeofenceRequest( geofence );
                addGeofence( geofenceRequest );
            } else {
                Log.e(TAG, "Geofence marker is null");
            }
        }

        static Intent makeNotificationIntent(Context geofenceService, String msg)
        {
            Log.d("Log",msg);
            return new Intent(geofenceService,tab3maps.class);
        }
}