package com.example.maishedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LessonsAdapter extends RecyclerView.Adapter<LessonsAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<Lessons> lessons;

    public LessonsAdapter(Context context, List<Lessons> lessons) {
        this.lessons = lessons;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public LessonsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.information, parent,false);
        return new LessonsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LessonsAdapter.ViewHolder holder, int viewType) {
        Lessons lesson = lessons.get(holder.getAdapterPosition());
        holder.lessonView.setText(lesson.getLesson());
        holder.teacherView.setText(lesson.getTeacher());
        holder.dateView.setText(lesson.getDate());
        holder.timeView.setText(lesson.getTime());
        holder.placeView.setText(lesson.getPlace());
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView lessonView, teacherView, dateView, timeView, placeView;
        ViewHolder(View view) {
            super(view);
            lessonView = (TextView) view.findViewById(R.id.lesson);
            teacherView = (TextView) view.findViewById(R.id.teacher);
            dateView = (TextView) view.findViewById(R.id.date);
            timeView = (TextView) view.findViewById(R.id.time);
            placeView = (TextView) view.findViewById(R.id.place);
        }
    }
}
