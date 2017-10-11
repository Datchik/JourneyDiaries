package com.tpandroid.cpe.journeydiaries;

import android.app.Activity;
import android.databinding.BaseObservable;

import com.tpandroid.cpe.journeydiaries.database.DatabaseInstance;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Louis-Marie on 09/10/2017.
 */

public class JourneyViewModel extends BaseObservable {
    private Journey journey;
    private Activity activity;
    private MainActivity mainActivity;
    private String state;

    public JourneyViewModel(Journey journey, Activity activity, MainActivity mainActivity) {
        this.journey = journey;
        this.activity = activity;
        this.mainActivity = mainActivity;
        this.state = journey.getId() == null ? "Create" : "Update";
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
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
}
