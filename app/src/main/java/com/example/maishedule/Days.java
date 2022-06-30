package com.example.maishedule;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Days {

    private int mDay;

    private List<Lessons> mLessons;

    public Days(int Day, List<Lessons> Lessons) {

        mDay = Day;
        mLessons = Lessons;
    }

    public int getDay() {return mDay;}

    public void setDay(int day) {mDay = day;}

    public List<Lessons> getLessons() {return mLessons;}

    public void setLessons(List<Lessons> lessons) {mLessons = lessons;}

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };
}
