package com.tpandroid.cpe.journeydiaries;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tpandroid.cpe.journeydiaries.database.DatabaseInstance;
import com.tpandroid.cpe.journeydiaries.databinding.JourneysFragmentBinding;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

/**
 * Created by Louis-Marie on 24/10/2017.
 */

public class AllJourneyMapFragment extends Fragment {

    private MapView mapView;
    private GoogleMap map;
    private List<Journey> journeys;
    private Activity activity;
    private GoogleApiClient gclient;
    private final CountDownLatch placeLatch = new CountDownLatch(1);


    @Override
    @TargetApi(11)
    public void onAttach(Activity context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    @Override
    @TargetApi(23)
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gclient = new GoogleApiClient.Builder(activity).addApi(Places.GEO_DATA_API).build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        JourneysFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.journeys_fragment,container,false);
        journeys = DatabaseInstance.getInstance(binding.getRoot().getContext()).getAllJourneys();



        for(Journey j : journeys){
            if(j.getPlaceId()!=null ) {
                // On lance la recherche de l'endroit pour remplir notre tableau
                addPlace(j.getPlaceId());
            }
        }

        View view = inflater.inflate(R.layout.fragment_journeys_map, container, false);
        Button button = (Button) view.findViewById(R.id.button_goback);
        // Listener "à la main", pour revenir sur l'écran principal
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getFragmentManager().popBackStack();
            }
        });
        mapView = (MapView) view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                map = mMap;

                /* on dezoome à max et on centre sur l'europe*/
                CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(49.6714,8.309));
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(2);

                map.moveCamera(center);
                map.animateCamera(zoom);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    private PendingResult<PlaceBuffer> placeResult;
    public void addPlace(String IdByGoogleApi){
        placeResult = Places.GeoDataApi.getPlaceById( gclient, IdByGoogleApi);
        placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
            @Override
            public void onResult(@NonNull PlaceBuffer places) {
                if (!places.getStatus().isSuccess()) {
                    // Request did not complete successfully
                    Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                    places.release();
                    return;
                }
                // Get the Place object from the buffer.
                final Place place = places.get(0);
                if (places.getStatus().isSuccess() && places.getCount() > 0) {
                    Place myPlace = places.get(0);
                    //System.out.println( "Place found: " + myPlace.getName());
                    map.addMarker(new MarkerOptions().position(myPlace.getLatLng()).title(myPlace.getName().toString()));
                } else {
                    //System.out.println( "Place not found");
                }

                // on libere le buffer
                places.release();
            }
        });

        //System.out.println("fin addPlace");
    }

    @Override
    public void onStart() {
        super.onStart();
        gclient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        gclient.disconnect();
    }


}
