package com.tpandroid.cpe.journeydiaries;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tpandroid.cpe.journeydiaries.databinding.NewJourneyFragmentBinding;

/**
 * Created by Louis-Marie on 09/10/2017.
 */

public class NewJourneyFragment extends Fragment{

    private Journey journey;
    private Activity activity;
    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(journey==null){
            journey=new Journey();
        }
        NewJourneyFragmentBinding binding = DataBindingUtil.inflate(inflater,R.layout.new_journey_fragment,container,false);
        mainActivity = (MainActivity)activity;
        JourneyViewModel jvm = new JourneyViewModel(journey, activity, mainActivity);
        binding.setJvm(jvm);
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

    public void setJourney(Journey journey) {
        this.journey = journey;
    }
}