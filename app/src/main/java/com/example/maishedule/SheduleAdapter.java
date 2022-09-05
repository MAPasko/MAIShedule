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
        void onSheduleClick(Shedule shedule,int position);
    }

    private final OnSheduleClickListener onClickListener;
    private final LayoutInflater inflater;
    private final List<Shedule> shedules;

    public SheduleAdapter(Context context, List<Shedule> shedules, OnSheduleClickListener onClickListener) {
        this.shedules = shedules;
        this.inflater = LayoutInflater.from(context);
        this.onClickListener = onClickListener;
    }

    @Override
    public SheduleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.week_list, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SheduleAdapter.ViewHolder holder, int viewType) {
        Shedule shedule = shedules.get(holder.getAdapterPosition());
        holder.weekView.setText(shedule.getWeek() + " неделя");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onSheduleClick(shedule, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return shedules.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView weekView;
        ViewHolder(View view) {
            super(view);
            weekView = (TextView) view.findViewById(R.id.week);
        }
    }
}
