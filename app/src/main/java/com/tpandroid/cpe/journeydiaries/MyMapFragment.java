package com.tpandroid.cpe.journeydiaries;

import android.annotation.TargetApi;
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
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
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
    private GoogleApiClient gclient;

    private static final int PLACE_PICKER_REQUEST = 1;


// https://stackoverflow.com/questions/20198750/android-mapfragment-within-a-fragment

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

        gclient = new GoogleApiClient.Builder(mainActivity).addApi(Places.GEO_DATA_API).build();

        // on utilise maps -> pas de fragment
        // activity.setContentView(R.layout.mymap_fragment);






        System.out.println("FINDME "+journey.getPlaceId());

        LatLngBounds bounds;
        if(journey.getPlaceId()!=null ) {
            // On lance la recherche de l'endroit.
            // La mise à jour sera alors faite depuis le @OnResult du CallBack
            getPlace(journey.getPlaceId());
        }else{
            // Appli n'a pas initialisé PlaceID
            System.out.println("on ne l'a pas");
            // on initialise le place picker avec une position par défault à CPE
            LatLng southwest = new LatLng(45.784606, 4.769657);
            LatLng northeast = new LatLng(45.784606, 4.969657);
            bounds = new LatLngBounds(southwest, northeast);
            updateLocalisation(bounds);
        }




    }

    private void updateLocalisation(LatLngBounds bounds){
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        builder.setLatLngBounds(bounds);
        try{
            Intent intent = builder.build(activity);
            startActivityForResult(intent, PLACE_PICKER_REQUEST);
        }catch(GooglePlayServicesRepairableException e) {
            System.out.println("GooglePlayServicesRepairableException");
            Toast.makeText(activity,"Votre version de Google Play Services n'est pas supportée",Toast.LENGTH_LONG).show();
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
            mainActivity.goBackLevelHomePage();

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
        PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById( gclient, IdByGoogleApi);
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
                    myPlace = places.get(0);
                    System.out.println( "Place found: " + myPlace.getName());
                    updateLocalisation(new LatLngBounds(myPlace.getLatLng(),myPlace.getLatLng()));
                } else {
                    System.out.println( "Place not found");
                }
                // on libere le buffer
                places.release();
            }
        });
        // pour debug
        if(myPlace!=null) {
            System.out.println("Place found: outside " + myPlace.getName());
        }else{
            System.out.println("place is null :-( ");
        }
        return myPlace;
    }


    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
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
