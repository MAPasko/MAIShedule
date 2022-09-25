package com.example.maishedule;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DaysFragment extends Fragment {

    List<Days> days;

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shedule_list, container, false);

        days = getArguments().getParcelableArrayList("days_info");

        RecyclerView sheduleRecycledView = (RecyclerView) view.findViewById(R.id.list);
        DaysAdapter.OnDayClickListener dayClickListener = new DaysAdapter.OnDayClickListener() {
            @Override
            public void onDayClick(Days day, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("lessons_info", (ArrayList<? extends Parcelable>) day.getLessons());
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                LessonsFragment lessonsFragment = new LessonsFragment();
                fragmentTransaction.add(R.id.container, lessonsFragment);
                lessonsFragment.setArguments(bundle);
                fragmentTransaction.addToBackStack("days");
                fragmentTransaction.commit();
            }
        };
        sheduleRecycledView.setAdapter(new DaysAdapter(getContext(), days, dayClickListener));

        return view;
    }
}
