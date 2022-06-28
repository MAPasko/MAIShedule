package com.example.maishedule;

import android.os.Parcel;
import android.os.Parcelable;

public class Shedule implements Parcelable {

    private String mLesson;

    private String mTeacher;

    private String mDate;

    private String mTime;

    private String mPlace;

    private int mId;

    public Shedule(String Lesson,String Teacher, String Date, String Time, String Place, int Id) {
        mLesson = Lesson;
        mTeacher = Teacher;
        mDate = Date;
        mTime = Time;
        mPlace = Place;
        mId = Id;
    }

    public String getLesson() {return mLesson;}

    public void setLesson(String lesson) {mLesson = lesson;}

    public String getTeacher() {return mTeacher;}

    public String getDate() {return mDate;}

    public String getTime() {return mTime;}

    public void setTime(String time) {mTime = time;}

    public String getPlace() {return mPlace;}

    public void setPlace(String place) {mTime = place;}

    public int getId() {return mId;}

    public Shedule(Parcel in){
        String[] data = new String[5];

        in.readStringArray(data);
        mLesson = data[0];
        mTeacher = data[1];
        mDate = data[2];
        mTime = data[3];
        mPlace = data[4];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                mLesson,
                mTeacher,
                mDate,
                mTime,
                mPlace
        });
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Shedule createFromParcel(Parcel in) {
            return new Shedule(in);
        }
        public Shedule[] newArray(int size) {
            return new Shedule[size];
        }
    };
}
