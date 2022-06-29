package com.example.maishedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class SheduleFragment extends AppCompatActivity implements LoaderCallbacks<List<Group>> {

    private static final String MAI_REQUEST_URL = "https://f3c1014e-8e89-49a9-8c30-13367dd34f68.mock.pstmn.io/education/schedule/detail.php";

    private static final int SHEDULE_LOADER_ID = 1;

    private SheduleAdapter mAdapter;

    private TextView mEmptyStateTextView;

    private ProgressBar mProgressBar;

    private Information mInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shedule_list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

        mProgressBar = (ProgressBar) findViewById(R.id.loading_indicator);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();

            loaderManager.initLoader(SHEDULE_LOADER_ID, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            mEmptyStateTextView.setText("Нет соединения с интернетом.");
        }
    }

    @Override
    public Loader<List<Group>> onCreateLoader(int i, Bundle bundle) {
        return new SheduleLoader(this, MAI_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Group>> loader,List<Group> groups) {
        mProgressBar.setVisibility(View.GONE);

        mEmptyStateTextView.setText("Расписание не найдено.");

        if(groups != null && !groups.isEmpty()) {
            mEmptyStateTextView.setVisibility(View.GONE);
            RecyclerView sheduleRecycledView = (RecyclerView) findViewById(R.id.list);
            SheduleAdapter.OnSheduleClickListener sheduleClickListener = new SheduleAdapter.OnSheduleClickListener() {
                @Override
                public void onSheduleClick(Shedule shedule, int position) {
                    //Intent infoIntent = new Intent(SheduleFragment.this, Information.class);
                    //infoIntent.putExtra("shedule",shedule);
                    //startActivity(infoIntent);
                }
            };
            mAdapter = new SheduleAdapter(this, groups, sheduleClickListener);
            sheduleRecycledView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Group>> loader) {
    }
}
