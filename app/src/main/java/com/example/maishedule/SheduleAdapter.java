package com.example.maishedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SheduleAdapter extends RecyclerView.Adapter<SheduleAdapter.ViewHolder> {

    interface OnSheduleClickListener{
        void onSheduleClick(Group groups,int position);
    }

    private final OnSheduleClickListener onClickListener;
    private final LayoutInflater inflater;
    private final List<Group> shedules;

    public SheduleAdapter(Context context, List<Group> groups, OnSheduleClickListener onClickListener) {
        this.shedules = groups;
        this.inflater = LayoutInflater.from(context);
        this.onClickListener = onClickListener;
    }

    @Override
    public SheduleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SheduleAdapter.ViewHolder holder, int viewType) {
        Group group = shedules.get(holder.getAdapterPosition());
        holder.timeView.setText(group.getTime());
        holder.lessonView.setText(group.getLesson());
        holder.placeView.setText(group.getPlace());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onSheduleClick(group, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return shedules.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView timeView, lessonView, placeView;
        ViewHolder(View view) {
            super(view);
            timeView = (TextView) view.findViewById(R.id.time);
            lessonView = (TextView) view.findViewById(R.id.lesson);
            placeView = (TextView) view.findViewById(R.id.place);
        }
    }
}
