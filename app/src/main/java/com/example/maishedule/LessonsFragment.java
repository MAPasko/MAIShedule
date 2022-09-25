package com.example.maishedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LessonsFragment extends Fragment {

    List<Lessons> lessons;

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shedule_list, container, false);

        lessons = getArguments().getParcelableArrayList("lessons_info");

        RecyclerView lessonsRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        lessonsRecyclerView.setAdapter(new LessonsAdapter(getContext(), lessons));

        return view;
    };
}
