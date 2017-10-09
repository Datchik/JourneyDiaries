package com.tpandroid.cpe.journeydiaries;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tpandroid.cpe.journeydiaries.databinding.JourneysFragmentBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Louis-Marie on 09/10/2017.
 */

public class JourneysFragment extends Fragment{



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        List<Journey> journeys = new ArrayList<>();
        journeys.add(new Journey("1", Calendar.getInstance(), Calendar.getInstance()));
        journeys.add(new Journey("2", Calendar.getInstance(), Calendar.getInstance()));
        journeys.add(new Journey("3", Calendar.getInstance(), Calendar.getInstance()));
        journeys.add(new Journey("4", Calendar.getInstance(), Calendar.getInstance()));
        JourneysFragmentBinding binding = DataBindingUtil.inflate(inflater,R.layout.journeys_fragment,container,false);
        binding.journeysList.setAdapter(new JourneyListAdapter(journeys));
        binding.journeysList.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        return binding.getRoot();
    }
}