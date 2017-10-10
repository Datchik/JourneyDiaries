package com.tpandroid.cpe.journeydiaries;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tpandroid.cpe.journeydiaries.databinding.MainActivityBinding;

public class MainActivity extends AppCompatActivity {
    private MainActivityBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        homePage();
    }

    public void homePage() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        JourneysFragment fragment = new JourneysFragment();
        fragment.setMainActivity(this);
        transaction.replace(R.id.fragment_container,fragment);
        transaction.commit();
    }


    public void newJourney() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        NewJourneyFragment fragment = new NewJourneyFragment();
        transaction.add(R.id.fragment_container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void editJourney(Journey journey) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        NewJourneyFragment fragment = new NewJourneyFragment();
        fragment.setJourney(journey);
        transaction.add(R.id.fragment_container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}