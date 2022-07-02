package com.example.maishedule;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.List;

public class Shedule implements Parcelable{

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

    public static Creator<Shedule> CREATOR = new Creator<Shedule>() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public Shedule createFromParcel(Parcel in) {
            return new Shedule(in);
        }
        @Override
        public Shedule[] newArray(int size) {
            return new Shedule[size];
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public Shedule (Parcel in) {
        mWeek = in.readInt();
        //mDays = in.readArrayList(null);
        mDays = in.readParcelableList(null, null);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mWeek);
        //dest.writeList(mDays);
        dest.writeParcelableList(mDays, 1);
    }
}
