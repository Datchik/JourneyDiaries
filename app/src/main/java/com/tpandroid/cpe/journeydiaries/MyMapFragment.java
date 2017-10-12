package com.tpandroid.cpe.journeydiaries;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.content.ContentValues.TAG;


/**
 * Created by Louis-Marie on 11/10/2017.
 */

public class MyMapFragment extends Fragment implements OnMapReadyCallback {



    private Journey journey;
    private Activity activity;
    private MainActivity mainActivity;
    private Place myPlace;

    private static final int PLACE_PICKER_REQUEST = 1;


// https://stackoverflow.com/questions/20198750/android-mapfragment-within-a-fragment

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity)context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // on utilise maps -> pas de fragment
        // activity.setContentView(R.layout.mymap_fragment);





        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        //builder.setLatLngBounds(bounds);
        System.out.println("FINDME "+journey.getPlaceId());
        if(getPlace(journey.getPlaceId())!=null) {
            LatLng placeLatLng = getPlace(journey.getPlaceId()).getLatLng();
            LatLng north = new LatLng(placeLatLng.latitude, placeLatLng.longitude + 0.1);
            LatLng south = new LatLng(placeLatLng.latitude, placeLatLng.longitude - 0.1);
            builder.setLatLngBounds(new LatLngBounds(north,south));
        }else{
            // on initialise le place picker avec une position par défault à CPE
            LatLng southwest = new LatLng(45.784606, 4.769657);
            LatLng northeast = new LatLng(45.784606, 4.969657);
            LatLngBounds bounds = new LatLngBounds(southwest, northeast);
            builder.setLatLngBounds(bounds);
        }


        try{
            Intent intent = builder.build(activity);
            startActivityForResult(intent, PLACE_PICKER_REQUEST);
        }catch(GooglePlayServicesRepairableException e) {
            System.out.println("GooglePlayServicesRepairableException");
        }catch(GooglePlayServicesNotAvailableException e){
            System.out.println("GooglePlayServicesNotAvailableException");
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST && resultCode == Activity.RESULT_OK) {
            Place place = PlacePicker.getPlace(activity, data);
            // Update UI with place information
            System.out.println("FINDME onectivityresult IF OK");
            System.out.println(place.getId());
            //journey.setId();

            LatLngBounds bounds = PlacePicker.getLatLngBounds(data);
            journey.setPlaceId(place.getId());
            mainActivity.setPickedPlaceId(place.getId());
            // Update UI with bounds

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in paris
        // and move the map's camera to the same location.
        LatLng paris = new LatLng(48.9034,2.5053);
        googleMap.addMarker(new MarkerOptions().position(paris)
             .title("Marker in Sydney"));


        googleMap.moveCamera(CameraUpdateFactory.newLatLng(paris));
    }

    public Journey getJourney() {
        return journey;
    }

    public void setJourney(Journey journey) {
        this.journey = journey;
    }

    public Place getPlace(String IdByGoogleApi){
        GoogleApiClient gclient = new GoogleApiClient.Builder(activity).addApi(Places.PLACE_DETECTION_API).addApi(Places.GEO_DATA_API).build();
        Places.GeoDataApi.getPlaceById( gclient, IdByGoogleApi).setResultCallback(new ResultCallback<PlaceBuffer>() {
            @Override
            public void onResult(@NonNull PlaceBuffer places) {
                if (places.getStatus().isSuccess() && places.getCount() > 0) {
                    myPlace = places.get(0);
                    Log.i(TAG, "Place found: " + myPlace.getName());
                } else {
                    Log.e(TAG, "Place not found");
                }
                places.release();
            }
        });
        return myPlace;
    }


    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

}
