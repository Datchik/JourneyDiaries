package com.tpandroid.cpe.journeydiaries;

import android.app.Activity;
import android.databinding.BaseObservable;

import com.tpandroid.cpe.journeydiaries.Journey;
import com.tpandroid.cpe.journeydiaries.MainActivity;

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

    public JourneyViewModel(Journey journey, Activity activity, MainActivity mainActivity) {
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
        mainActivity.editJourney(journey);
    }

    public void newJourneyClick() {
        mainActivity.newJourney();
    }

    public void createNewJourney(String name, Calendar departure_date, Calendar return_date) {
        journey.setName(name);
        journey.setFrom(departure_date);
        journey.setTo(return_date);
        activity.setTitle(R.string.app_name);
        mainActivity.homePage();
    }

    public void returnToMainActivity() {
        mainActivity.homePage();
    }

}
