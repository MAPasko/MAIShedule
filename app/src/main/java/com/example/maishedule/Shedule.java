package com.example.maishedule;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Shedule {

    private int mWeek;

    private List<Days> mDays;

    public Shedule(int Week, List<Days> Days) {

        mWeek = Week;
        mDays = Days;
    }

    public int getWeek() {return mWeek;}

    public void setWeek(int week) {mWeek = week;}

    public List<Days> getDays() {return mDays;}

    public void setDays(List<Days> days) {mDays = days;}

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };
}
