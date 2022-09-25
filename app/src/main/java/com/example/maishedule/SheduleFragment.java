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
import java.util.Calendar;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class SheduleFragment extends Fragment {

    List<Shedule> weeks;
    List<Days> days;
    List<Lessons> lessons;
    int currentPosition = 0;

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.days_list/*shedule_list*/, container, false);

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

        Calendar calendar = Calendar.getInstance();
        int Week = calendar.get(Calendar.WEEK_OF_YEAR) - 35;
        int Day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (Day == 0)
            Day = 7;
        Log.e("ПРОВЕРКА", String.valueOf(Day));

        Shedule currentWeek = weeks.get(Week);
        days = currentWeek.getDays();
        Days currentDay = days.get(Day - 1);
        currentPosition = Day - 1;
        lessons = currentDay.getLessons();

        RecyclerView dayRecyclerView = (RecyclerView) view.findViewById(R.id.days);
        DaysAdapter.OnDayClickListener dayClickListener = new DaysAdapter.OnDayClickListener() {
            @Override
            public void onDayClick(Days day, int position) {
                dayRecyclerView.getChildAt(currentPosition).setBackgroundColor(0xFFBB86FC);
                dayRecyclerView.getChildAt(position).setBackgroundColor(0xFF6200EE);
                currentPosition = position;
                lessons = day.getLessons();
                RecyclerView lessonsRecyclerView = (RecyclerView) view.findViewById(R.id.lessons);
                lessonsRecyclerView.setAdapter(new LessonsAdapter(getContext(), lessons));
            }
        };
        dayRecyclerView.setAdapter(new DaysAdapter(getContext(), days, dayClickListener));

        RecyclerView lessonsRecyclerView = (RecyclerView) view.findViewById(R.id.lessons);
        lessonsRecyclerView.setAdapter(new LessonsAdapter(getContext(), lessons));

        //RecyclerView sheduleRecycledView = (RecyclerView) view.findViewById(R.id.list);
        //SheduleAdapter.OnSheduleClickListener sheduleClickListener = new SheduleAdapter.OnSheduleClickListener() {
        //    @Override
        //    public void onSheduleClick(Shedule shedule, int position) {
        //        Bundle bundle = new Bundle();
        //        bundle.putParcelableArrayList("days_info", (ArrayList<? extends Parcelable>) shedule.getDays());
        //        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        //        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //        DaysFragment daysFragment = new DaysFragment();
        //        fragmentTransaction.add(R.id.container, daysFragment);
        //        daysFragment.setArguments(bundle);
        //        fragmentTransaction.addToBackStack("weeks");
        //        fragmentTransaction.commit();
        //    }
        //};
        //sheduleRecycledView.setAdapter(new SheduleAdapter(getContext(), weeks, sheduleClickListener));

        return view;
    }
}
