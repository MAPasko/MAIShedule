package com.example.maishedule;

import android.os.Parcel;
import android.os.Parcelable;

public class Days {

    private int mDay;

    private Lessons mLessons;

    public Days(int Day, Lessons Lessons) {

        mDay = Day;
        mLessons = Lessons;
    }

    public int getDay() {return mDay;}

    public void setDay(int day) {mDay = day;}

    public Lessons getLessons() {return mLessons;}

    public void setLessons(Lessons lessons) {mLessons = lessons;}

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };
}
