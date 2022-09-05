package com.example.maishedule;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.List;

public class Days implements Parcelable{

    private String mDay;

    private List<Lessons> mLessons;

    public Days(String Day, List<Lessons> Lessons) {

        mDay = Day;
        mLessons = Lessons;
    }

    public String getDay() {return mDay;}

    public void setDay(String day) {mDay = day;}

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
        mDay = in.readString();
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
        dest.writeString(mDay);
        dest.writeList(mLessons);
        dest.writeParcelableList(mLessons, 1);
    }
}
