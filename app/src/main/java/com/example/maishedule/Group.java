package com.example.maishedule;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.List;

public class Group implements Parcelable {

    private String mGroup;

    private List<Shedule> mShedule;

    public Group(String Group, List<Shedule> Shedule) {

        mGroup = Group;
        mShedule = Shedule;
    }

    public String getGroup() {return mGroup;}

    public void setGroup(String group) {mGroup = group;}

    public List<Shedule> getmShedule() {return mShedule;}

    public void setShedule(List<Shedule> shedule) {mShedule = shedule;}

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public Group(Parcel in){
        mGroup = in.readString();
        //mShedule = in.readArrayList(null);
        mShedule = in.readParcelableList(null, null);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mGroup);
        //dest.writeList(mShedule);
        dest.writeParcelableList( mShedule, 1);
    }

    public static Creator<Group> CREATOR = new Creator<Group>() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }
        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };
}
