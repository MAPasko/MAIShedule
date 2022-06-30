package com.example.maishedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder>{

    interface OnGroupClickListener {
        void onGroupClick(Group shedule,int position);
    }

    private final OnGroupClickListener onClickListener;
    private final LayoutInflater inflater;
    private final List<Group> groups;

    public GroupAdapter(Context context, List<Group> groups, OnGroupClickListener onClickListener) {
        this.groups = groups;
        this.inflater = LayoutInflater.from(context);
        this.onClickListener = onClickListener;
    }

    @Override
    public GroupAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.group_list,parent,false);
        return new GroupAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupAdapter.ViewHolder holder, int viewType) {
        Group group = groups.get(holder.getAdapterPosition());
        holder.groupView.setText(group.getGroup());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onGroupClick(group, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView groupView;
        ViewHolder(View view) {
            super(view);
            groupView = (TextView) view.findViewById(R.id.group);
        }
    }
}
