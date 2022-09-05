package com.example.maishedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.ViewHolder> {

    interface OnDayClickListener{
        void onDayClick(Days day,int position);
    }

    private final DaysAdapter.OnDayClickListener onClickListener;
    private final LayoutInflater inflater;
    private final List<Days> days;

    public DaysAdapter(Context context, List<Days> days, DaysAdapter.OnDayClickListener onClickListener) {
        this.days = days;
        this.inflater = LayoutInflater.from(context);
        this.onClickListener = onClickListener;
    }

    @Override
    public DaysAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.week_list, parent,false);
        return new DaysAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DaysAdapter.ViewHolder holder, int viewType) {
        Days day = days.get(holder.getAdapterPosition());
        holder.dayView.setText(day.getDay());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onDayClick(day, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView dayView;
        ViewHolder(View view) {
            super(view);
            dayView = (TextView) view.findViewById(R.id.week);
        }
    }
}
