package com.example.maishedule;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class SheduleLoader extends AsyncTaskLoader<List<Group>> {

    private String mUrl;
    /**
     * Constructs a new {@link SheduleLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public SheduleLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Group> loadInBackground() {
        if(mUrl==null) {
            return null;
        }
        List<Group> groups = QueryUtils.fetchSheduleData(mUrl);
        return groups;
    }
}
