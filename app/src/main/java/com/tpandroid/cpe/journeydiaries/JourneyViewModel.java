package com.tpandroid.cpe.journeydiaries;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.databinding.BaseObservable;

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

    JourneyViewModel(Journey journey, Activity activity, MainActivity mainActivity) {
        this.journey = journey;
        this.activity = activity;
        this.mainActivity = mainActivity;
    }
    public String getName() {
        return journey.getName();
    }
    public String getFrom() {
        Calendar cal = journey.getFrom();
        DateFormat sdf = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM,
                Locale.getDefault());
        return sdf.format(cal.getTime());
    }
    public String getTo() {
        Calendar cal = journey.getTo();
        DateFormat sdf = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM,
                Locale.getDefault());
        return sdf.format(cal.getTime());
    }
    public void onJourneyClick() {
        if(activity != null) {
            activity.setTitle(getName());
        }
    }

    public void newJourneyClick() {
        mainActivity.newJourney();
    }

    public void createNewJourney() {
        activity.setTitle("Vous avez gagné un voyage !!!");
    }

    public void cancelNewJourney() {
        mainActivity.homePage();
    }
}
