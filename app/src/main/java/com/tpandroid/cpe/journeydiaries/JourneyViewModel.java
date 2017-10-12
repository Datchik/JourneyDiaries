package com.tpandroid.cpe.journeydiaries;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.BaseObservable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.tpandroid.cpe.journeydiaries.database.DatabaseInstance;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * Created by Louis-Marie on 09/10/2017.
 */

public class JourneyViewModel extends BaseObservable {
    private Journey journey;
    private Activity activity;
    private MainActivity mainActivity;
    private String state;
    private Place myPlace;

    public JourneyViewModel(Journey journey, Activity activity, MainActivity mainActivity) {
        this.journey = journey;
        this.activity = activity;
        this.mainActivity = mainActivity;
        this.state = journey.getId() == null ? activity.getString(R.string.create_travel) : activity.getString(R.string.update_travel);
    }

    public String getState() {
        return state;
    }
    public String getName() {
        return journey.getName();
    }
    public String getFrom() {
        return journey.getFrom();
    }
    public String getTo() {
        return journey.getTo();
    }
    public Integer getId() {
        return journey.getId();
    }
    public String getNote() {
        return journey.getNote();
    }
    public Place getPlace(){
        GoogleApiClient gclient = new GoogleApiClient.Builder(activity).addApi(Places.PLACE_DETECTION_API).addApi(Places.GEO_DATA_API).build();
        Places.GeoDataApi.getPlaceById( gclient, journey.getPlaceId()).setResultCallback(new ResultCallback<PlaceBuffer>() {
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

    public String getTextButton(){
        if(journey.getId() == null){
            return String.valueOf(R.string.create_travel);
        }
        else{
            return String.valueOf(R.string.update_travel);
        }
    }

    public void onJourneyClick() {
        if(activity != null) {
            activity.setTitle(getName());
        }
        mainActivity.editJourney(journey);
    }

    public void newJourneyClick() {
        mainActivity.newJourney();
    }

    public void createNewJourney(String name, String departure_date, String return_date) {
        if(state == "Create"){
            DatabaseInstance.getInstance(mainActivity).createJourney(name, departure_date, return_date);
        }
        else if(state == "Update"){
            DatabaseInstance.getInstance(mainActivity).updateJourney(getId(), name, departure_date, return_date);
        }
        activity.setTitle(R.string.app_name);
        mainActivity.homePage();
    }

    public void returnToMainActivity() {
        activity.setTitle(R.string.app_name);
        mainActivity.homePage();
    }

    public void setReturnDate() {
        mainActivity.showDialog(999);
    }

    public void setDepartureDate() {
        mainActivity.showDialog(998);
    }

    public void setJourneyPlace(){
        mainActivity.setPlaceJourney(journey);
    }
}
