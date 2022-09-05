package com.example.maishedule;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Objects;

public class SheduleFragment extends Fragment {

    List<Shedule> weeks;

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shedule_list, container, false);

        List<Group> groups = getArguments().getParcelableArrayList("parced_info");
        SharedPreferences sPref =  getActivity().getSharedPreferences("sPref", Context.MODE_PRIVATE);
        String group = sPref.getString("sPref",null);
        for (int i = 0; i < groups.size(); i++) {
            Group currentGroup = groups.get(i);
            if (Objects.equals(currentGroup.getGroup(), group)) {
                weeks = currentGroup.getShedule();
                break;
            }
        }

        RecyclerView sheduleRecycledView = (RecyclerView) view.findViewById(R.id.list);
        SheduleAdapter.OnSheduleClickListener sheduleClickListener = new SheduleAdapter.OnSheduleClickListener() {
            @Override
            public void onSheduleClick(Shedule shedule, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("days_info", (ArrayList<? extends Parcelable>) shedule.getDays());
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                DaysFragment daysFragment = new DaysFragment();
                //fragmentTransaction.replace(R.id.container, daysFragment, "sh_fr");
                fragmentTransaction.add(R.id.container, daysFragment);
                daysFragment.setArguments(bundle);
                fragmentTransaction.addToBackStack("weeks");
                fragmentTransaction.commit();
            }
        };
        sheduleRecycledView.setAdapter(new SheduleAdapter(getContext(), weeks, sheduleClickListener));

        return view;
    }
}
