package com.tpandroid.cpe.journeydiaries;

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

import com.tpandroid.cpe.journeydiaries.databinding.JourneysFragmentBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Louis-Marie on 09/10/2017.
 */

public class NewJourneyFragment extends Fragment{

    private  Activity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        JourneysFragmentBinding binding = DataBindingUtil.inflate(inflater,R.layout.new_journey_fragment,container,false);
        binding.journeysList.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }
}