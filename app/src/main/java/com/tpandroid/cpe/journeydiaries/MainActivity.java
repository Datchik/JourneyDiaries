package com.tpandroid.cpe.journeydiaries;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.tpandroid.cpe.journeydiaries.databinding.MainActivityBinding;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.widget.DatePicker;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateViewFrom;
    private TextView dateViewTo;
    private String pickedPlaceId;
    private int year, month, day;

    private MainActivityBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        homePage();
        dateViewFrom = (TextView) findViewById(R.id.from);
        dateViewTo = (TextView) findViewById(R.id.to);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
       // showDate(year, month+1, day);
    }

    public void homePage() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        JourneysFragment fragment = new JourneysFragment();
        fragment.setMainActivity(this);
        transaction.replace(R.id.fragment_container,fragment);
        //transaction.addToBackStack("home");
        transaction.commit();
    }

    public void goBackLevelHomePage(){
        FragmentManager manager = getFragmentManager();
        manager.popBackStack();
    }

    public void goHomePage(){
        FragmentManager manager = getFragmentManager();
        manager.popBackStack(manager.getBackStackEntryCount(),manager.POP_BACK_STACK_INCLUSIVE);
    }

    public void newJourney() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        NewJourneyFragment fragment = new NewJourneyFragment();
        transaction.replace(R.id.fragment_container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void editJourney(Journey journey) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        NewJourneyFragment fragment = new NewJourneyFragment();
        fragment.setJourney(journey);
        transaction.replace(R.id.fragment_container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void setPlaceJourney(Journey journey) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        MyMapFragment fragment = new MyMapFragment();
        fragment.setMainActivity(this);
        fragment.setJourney(journey);
        transaction.add(R.id.fragment_container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void viewMap(){
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        AllJourneyMapFragment fragment = new AllJourneyMapFragment();
        transaction.add(R.id.fragment_container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



    @Override
    @SuppressWarnings("deprecation")
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateToListener, year, month, day);
        }
        if (id == 998) {
            return new DatePickerDialog(this,
                    myDateFromListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateFromListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
           showFromDate(arg1, arg2+1, arg3);
        }
    };

    private DatePickerDialog.OnDateSetListener myDateToListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            showToDate(arg1, arg2+1, arg3);
        }
    };

    public void showFromDate(int year, int month, int day) {
        TextView df = (TextView)findViewById(R.id.from);
        df.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));
    }
    public void showToDate(int year, int month, int day) {

        TextView df = (TextView)findViewById(R.id.to);
        df.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));
    }

/*
    @TargetApi(23)
    private boolean canAccessLocation() {
        return(PackageManager.PERMISSION_GRANTED==checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }*/


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //do some work
    }


    public String getPickedPlaceId() {
        return pickedPlaceId;
    }
    public void setPickedPlaceId(String pickedPlaceId) {
        this.pickedPlaceId = pickedPlaceId;
    }
}