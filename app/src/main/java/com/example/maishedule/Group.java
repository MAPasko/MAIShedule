package com.example.maishedule;

import android.os.Parcel;
import android.os.Parcelable;

public class Group implements Parcelable {

    private String mGroup;

    private Shedule mShedule;

    public Group(String Group, Shedule Shedule) {

        mGroup = Group;
        mShedule = Shedule;
    }

    public String getGroup() {return mGroup;}

    public void setGroup(String group) {mGroup = group;}

    public Shedule getmShedule() {return mShedule;}

    public void setShedule(Shedule shedule) {mShedule = shedule;}

    public Group(Parcel in){
        String[] data = new String[1];

        in.readStringArray(data);
        mGroup = data[0];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                mGroup
        });
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };
}
