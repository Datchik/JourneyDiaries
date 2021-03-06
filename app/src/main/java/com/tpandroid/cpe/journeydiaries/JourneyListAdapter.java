package com.tpandroid.cpe.journeydiaries;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tpandroid.cpe.journeydiaries.databinding.JourneyItemBinding;

import java.util.List;


/**
 * Created by Louis-Marie on 09/10/2017.
 */

class JourneyListAdapter extends RecyclerView.Adapter<JourneyListAdapter.BindingHolder>
{
    private List<Journey> journeys;
    private Activity activity;
    private MainActivity mainActivity;
    JourneyListAdapter(List<Journey> journeys, Activity activity,  MainActivity mainActivity) {
        this.journeys = journeys;
        this.activity = activity;
        this.mainActivity = mainActivity;
    }
    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        JourneyItemBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.journey_item,parent,false);
        return new BindingHolder(binding);
    }
    @Override
    public void onBindViewHolder(JourneyListAdapter.BindingHolder holder, int
            position) {
        JourneyItemBinding binding = holder.binding;
        Journey journey = journeys.get(position);
        binding.setJvm(new JourneyViewModel(journeys.get(position), this.activity, this.mainActivity));
        //binding.name.setText(journey.getName());
        //Calendar cal = journey.getFrom();
        //DateFormat sdf = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM, Locale.getDefault());
        //binding.startDate.setText(sdf.format(cal.getTime()));
        //cal = journey.getTo();
        //binding.endDate.setText(sdf.format(cal.getTime()));
    }
    @Override
    public int getItemCount() {
        return journeys.size();
    }
    static class BindingHolder extends RecyclerView.ViewHolder {
        private JourneyItemBinding binding;
        BindingHolder(JourneyItemBinding binding) {
            super(binding.journeyItem);
            this.binding = binding;
        }
    }
}