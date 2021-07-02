package com.example.HealthGO.main_menu_screen;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.content.AsyncTaskLoader;

import com.example.HealthGO.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentId;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback{


    // Variable that use for checking service
    private static final String TAG = "MapFragment";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    //Variable that use for getting device location permission
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private boolean mLOCATION_PERMISSION_GRANTED = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    //Variable that use for initializing google map
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final float DEFAULT_ZOOM = 15f;
    private EditText mSearchText;
    private SupportMapFragment mapFragment;
    // Variable that use for getting nearby place
    private ImageButton btnNearByPlace;
    private double currentLat = 0;
    private double currentLong = 0;
    private ImageButton btnCurrentLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        //mSearchText = getView().findViewById(R.id.input_search);
        //Get location permission
        getLocationPermission();
        //Initialize map
        if(isServiceOK()){
            initMap();
        }
        //Async map
//        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap googleMap) {
//                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                    @Override
//                    public void onMapClick(LatLng latLng) {
//                        //When click on map, initialize marker options
//                        MarkerOptions markerOptions = new MarkerOptions();
//                        //Set position of marker
//                        markerOptions.position(latLng);
//                        //Set title of marker
//                        markerOptions.title(latLng.latitude + " : " + latLng.longitude);
//                        //Remove all marker
//                        googleMap.clear();
//                        //Animating to zoom the marker
//                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                                latLng, 10
//                        ));
//                        //Add marker on map
//                        googleMap.addMarker(markerOptions);
//                    }
//                });
//            }
//        });

        return view;
    }

    public boolean isServiceOK(){
        Log.d(TAG, "isServicesOK: Checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getContext());

        if(available== ConnectionResult.SUCCESS){
            Log.d(TAG, "isServiceOK: Google Play services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "isServiceOK: an error appear but we can not fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MapFragment.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else{
            Toast.makeText(getContext(), "We can not make up request", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: Map is ready");
        mMap = googleMap;
        if (mLOCATION_PERMISSION_GRANTED) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mSearchText = getView().findViewById(R.id.input_search);
            btnCurrentLocation = getView().findViewById(R.id.btnCurrentLocation);
            btnNearByPlace = getView().findViewById(R.id.btnNearby);
            mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map_fragment);
            initMapSearchBar();

            btnCurrentLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDeviceLocation();
                }
            });

            btnNearByPlace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    getNearByPlace();
                    showNearByRestaurant();
                }
            });

        }
    }

    private void initMap(){
        Log.d(TAG, "initMap: Initializing google map");
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map_fragment);
        supportMapFragment.getMapAsync(MapFragment.this);
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: Getting location permission");
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                    COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLOCATION_PERMISSION_GRANTED = true;
                initMap();
            } else{
                ActivityCompat.requestPermissions(getActivity(), permission,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else{
            ActivityCompat.requestPermissions(getActivity(), permission,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void initMapSearchBar(){
        Log.d(TAG, "initMapSearchBar: initializing");
        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_DONE
                || event.getAction() == KeyEvent.ACTION_DOWN
                || event.getAction() == KeyEvent.KEYCODE_ENTER){
                    //Execute our method for searching
                    geoLocate();
                }
                return true;
            }
        });
        //hideSoftKeyBoard();
    }

    private void geoLocate(){
        Log.d(TAG, "geoLocate: geolocating");

        String searchString = mSearchText.getText().toString();
        Geocoder geocoder = new Geocoder(getContext());
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString, 1);
        } catch (IOException e){
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
        }
        if(list.size() > 0){
            Address address = list.get(0);
            Log.d(TAG, "geoLocate: found a location: " + address.toString());
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, address.getAddressLine(0));
        }
    }

    private void showNearByRestaurant(){
        ArrayList<LatLng> place = new ArrayList<>();
        LatLng quan1 = new LatLng(11.39, 106.23);
        LatLng quan2 = new LatLng(11.386, 106.23);
        LatLng quan3 = new LatLng(11.391, 106.23);
        place.add(quan1);
        place.add(quan2);
        place.add(quan3);

        for(int i = 0; i <= 2; i++){
            mMap.addMarker(new MarkerOptions().position(place.get(i)).title("Quan " + i));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(place.get(i)));
        }

//        final double lat = 11.39;
//        final double longi = 106.23;
//        LatLng pos = new LatLng(lat, longi);
//        mMap.addMarker(new MarkerOptions().position(pos).title("test place"));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));

//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(pos);
//        markerOptions.title("Test place");
        //moveCamera(new LatLng(lat, longi), DEFAULT_ZOOM, "Test place");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: Called");
        mLOCATION_PERMISSION_GRANTED = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLOCATION_PERMISSION_GRANTED = false;
                            Log.d(TAG, "onRequestPermissionsResult: Permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: Permission granted");
                    mLOCATION_PERMISSION_GRANTED = true;
                    initMap();
                }
            }
        }

    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: Getting the device current location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        try {
            if (mLOCATION_PERMISSION_GRANTED) {
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: Found location");
                            Location currentLocation = (Location) task.getResult();

                            // save the current location into global variable
                            currentLat = currentLocation.getLatitude();
                            currentLong = currentLocation.getLongitude();
                            moveCamera(new LatLng(currentLat, currentLong), DEFAULT_ZOOM, "My location");
                            showNearByRestaurant();
//                            Toast.makeText(getContext(), "lat:" + currentLat + "\tLong:" + currentLong, Toast.LENGTH_LONG).show();
//                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM, "My location");
                        } else {
                            Log.d(TAG, "onComplete: Your current location is null");
                            Toast.makeText(getActivity(), "Unable to get your current location", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d(TAG, "moveCamera: Moving camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));


        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title(title);
        mMap.addMarker(options);
        //hideSoftKeyBoard();
    }

    private void getNearByPlace(){

        //Initialize list of place type
        String placeType = "restaurant";
        //initialize list of place name
        String placeName = "restaurant";

        getDeviceLocation();


        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
                "?location=" + currentLat + "," + currentLong + // current location
                "&radius=500" + //nearby radius
                "&types=" + placeType + // place type
                "&sensor=true" + //sensor
                "&key=" + getResources().getString(R.string.GoogleMapKey);

        new PlaceTask().execute(url);
    }

    private class PlaceTask extends AsyncTask<String,Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            //Initialize data
            String data = null;
            try {
                data = downloadUrl(strings[0]);
            } catch (IOException e){
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            //Execute parser task
            new ParserTask().execute(s);
        }
    }

    private String downloadUrl(String string) throws IOException{
        URL url = new URL(string);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        InputStream stream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null){
            builder.append(line);
        }
        String data = builder.toString();
        reader.close();
        return data;
    }

    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {
        @Override
        protected List<HashMap<String, String>> doInBackground(String... strings) {
            //Create json parser class
            JsonParser jsonParser = new JsonParser();
            List<HashMap<String, String>> mapList = null;
            JSONObject object = null;
            try{
                object = new JSONObject(strings[0]);
                mapList = jsonParser.parseResult(object);
            } catch (JSONException e){
                e.printStackTrace();
            }
            return mapList;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
            mMap.clear();
            for(int i = 0; i < hashMaps.size(); i++){
                HashMap<String, String> hashMapList = hashMaps.get(i);

                double lat = Double.parseDouble(hashMapList.get("lat"));
                double lng = Double.parseDouble(hashMapList.get("lng"));
                String name = hashMapList.get("name");

                LatLng latLng = new LatLng(lat, lng);
                MarkerOptions options = new MarkerOptions();
                options.position(latLng);
                options.title(name);
                mMap.addMarker(options);
            }
        }
    }

//    private void hideSoftKeyBoard(){
//        this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//    }
}