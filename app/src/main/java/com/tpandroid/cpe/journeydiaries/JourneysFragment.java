package com.tpandroid.cpe.journeydiaries;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tpandroid.cpe.journeydiaries.database.DatabaseAccess;
import com.tpandroid.cpe.journeydiaries.database.DatabaseInstance;
import com.tpandroid.cpe.journeydiaries.databinding.JourneysFragmentBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Louis-Marie on 09/10/2017.
 */

public class JourneysFragment extends Fragment{

    private Activity activity;
    private MainActivity mainActivity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        JourneysFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.journeys_fragment,container,false);
        List<Journey> journeys = DatabaseInstance.getInstance(binding.getRoot().getContext()).getAllJourneys();

        binding.journeysList.setAdapter(new JourneyListAdapter(journeys, activity, mainActivity));
        binding.journeysList.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        binding.setJvm(new JourneyViewModel(new Journey(), activity, mainActivity));
        return binding.getRoot();
    }

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

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
}