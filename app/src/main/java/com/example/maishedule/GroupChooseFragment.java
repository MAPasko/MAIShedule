package com.example.maishedule;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class GroupChooseFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Shedule groups = getArguments().getParcelable("parced_info");

        return inflater.inflate(R.layout.choose_group, container, false);
    }
}
