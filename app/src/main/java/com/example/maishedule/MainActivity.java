package com.example.maishedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Group>> {

    private static final String MAI_REQUEST_URL = "https://f3c1014e-8e89-49a9-8c30-13367dd34f68.mock.pstmn.io/education/schedule/detail.php";

    private static final int SHEDULE_LOADER_ID = 1;

    private SheduleAdapter mAdapter;

    private TextView mEmptyStateTextView;

    private ProgressBar mProgressBar;

    private Information mInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        // получаем экземпляр FragmentTransaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // добавляем фрагмент
        GroupChooseFragment groupChooseFragment = new GroupChooseFragment();
        fragmentTransaction.add(R.id.container, groupChooseFragment);
        fragmentTransaction.commit();

        TextView startSearch = (TextView) findViewById(R.id.start);
        startSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        //Intent sheduleIntent = new Intent(MainActivity.this, SheduleFragment.class);
                        //startActivity(sheduleIntent);
            }
        });
    }

    @Override
    public Loader<List<Group>> onCreateLoader(int i, Bundle bundle) {
        return new SheduleLoader(this, MAI_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Group>> loader, List<Group> groups) {
        mProgressBar.setVisibility(View.GONE);
        Bundle bundle =new Bundle();
        bundle.putParcelable("parced_info", (Parcelable) groups);
        GroupChooseFragment info = new GroupChooseFragment();
        info.setArguments(bundle);
    }

    @Override
    public void onLoaderReset(Loader<List<Group>> loader) {
    }
}