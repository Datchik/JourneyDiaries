package com.tpandroid.cpe.journeydiaries;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tpandroid.cpe.journeydiaries.databinding.NewJourneyFragmentBinding;


/**
 * Created by Louis-Marie on 11/10/2017.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback {



    private Journey journey;


    private Activity activity;
    private MainActivity mainActivity;

    private static final int PLACE_PICKER_REQUEST = 1;
    private static final int RESULT_OK = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(journey==null){
            journey=new Journey();
        }
        NewJourneyFragmentBinding binding = DataBindingUtil.inflate(inflater,R.layout.map_fragment,container,false);
        mainActivity = (MainActivity)activity;
        binding.setJvm(new JourneyViewModel(journey, activity, mainActivity));
        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity.setContentView(R.layout.map_fragment);

        LatLng southwest = new LatLng(48.6187741, 1.93000);
        LatLng northeast = new LatLng(49.500000, 2.8000);
        LatLngBounds bounds = new LatLngBounds(southwest, northeast);

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        builder.setLatLngBounds(bounds);

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
        if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK) {
            Place place = PlacePicker.getPlace(activity, data);
            // Update UI with place information
            System.out.println(place.getId());

            LatLngBounds bounds = PlacePicker.getLatLngBounds(data);
            // Update UI with bounds
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
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

    public Activity getMainActivity() {
        return activity;
    }

    public void setMainActivity(Activity mainActivity) {
        this.activity = mainActivity;
    }
}
