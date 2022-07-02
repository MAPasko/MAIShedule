package com.example.maishedule;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.List;

public class Days implements Parcelable{

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

    public static Creator<Days> CREATOR = new Creator<Days>() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public Days createFromParcel(Parcel in) {
            return new Days(in);
        }
        @Override
        public Days[] newArray(int size) {
            return new Days[size];
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public Days (Parcel in) {
        mDay = in.readInt();
        //mLessons = in.readArrayList(null);
        mLessons = in.readParcelableList(null, null);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mDay);
        dest.writeList(mLessons);
        dest.writeParcelableList(mLessons, 1);
    }
}
