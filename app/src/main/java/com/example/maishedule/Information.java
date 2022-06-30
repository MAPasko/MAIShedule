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
        Lessons lessons = (Lessons) data.getParcelable("lessons");

        TextView lessonView = (TextView) findViewById(R.id.lesson);
        lessonView.setText(lessons.getLesson());


        TextView teacherView = (TextView) findViewById(R.id.teacher);
        teacherView.setText(lessons.getTeacher());


        TextView timeView = (TextView) findViewById(R.id.time);
        timeView.setText(lessons.getTime());


        TextView placeView = (TextView) findViewById(R.id.place);
        placeView.setText(lessons.getPlace());
    }
}
