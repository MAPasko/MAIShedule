package com.example.maishedule;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Information extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);

        Bundle data = getIntent().getExtras();
        Shedule shedule = (Shedule) data.getParcelable("shedule");

        TextView lessonView = (TextView) findViewById(R.id.lesson);
        lessonView.setText(shedule.getLesson());


        TextView teacherView = (TextView) findViewById(R.id.teacher);
        teacherView.setText(shedule.getTeacher());


        TextView dateView = (TextView) findViewById(R.id.date);
        dateView.setText(shedule.getDate());


        TextView timeView = (TextView) findViewById(R.id.time);
        timeView.setText(shedule.getTime());


        TextView placeView = (TextView) findViewById(R.id.place);
        placeView.setText(shedule.getPlace());
    }
}
