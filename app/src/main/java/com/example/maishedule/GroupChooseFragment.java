package com.example.maishedule;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GroupChooseFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_group, container, false);

       // if(getArguments() != null)
        List<Group> groups = getArguments().getParcelableArrayList("parced_info");

        RecyclerView groupRecycledView = (RecyclerView) view.findViewById(R.id.container);
        GroupAdapter .OnGroupClickListener groupClickListener = new GroupAdapter.OnGroupClickListener() {
            @Override
            public void onGroupClick(Group group, int position) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sPref",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("sPref",group.getGroup());
                editor.apply();
                getActivity().getFragmentManager().popBackStack();
                //getActivity().getSupportFragmentManager().popBackStackImmediate();
                //Log.e("ПРОВЕРКА", "aaaaaaaaaaaaaa");
            }
        };
        //mAdapter = new GroupAdapter(this, groups, groupClickListener);
        groupRecycledView.setAdapter(new GroupAdapter(getContext(),groups,groupClickListener));
    return view;
    }

    public void onDestroyView() {
        super.onDestroyView();
    }
}
