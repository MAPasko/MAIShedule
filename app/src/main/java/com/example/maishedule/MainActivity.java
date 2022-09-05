package com.example.maishedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Group>> {

    private static final String MAI_REQUEST_URL = "https://f3c1014e-8e89-49a9-8c30-13367dd34f68.mock.pstmn.io/education/schedule/detail.php";

    private static final int SHEDULE_LOADER_ID = 1;

    private Button mButton;

    private TextView mEmptyStateTextView;

    private ProgressBar mProgressBar;

    private Bundle bundle = new Bundle();

    private SharedPreferences sPref;

    // получаем экземпляр FragmentManager
    FragmentManager fragmentManager = getSupportFragmentManager();


    //основные действия при создании главной активности
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.start);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

        mProgressBar = (ProgressBar) findViewById(R.id.loading_indicator);

        //создание сетевого подключения
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

        //привязка прослушивания нажатия к кнопке "найти расписание"
        TextView startSearch = (TextView) findViewById(R.id.start);
        startSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButton.setVisibility(View.GONE);
                FragmentTransaction newTransaction = fragmentManager.beginTransaction();
                SheduleFragment sheduleFragment = new SheduleFragment();
                newTransaction.add(R.id.container, sheduleFragment);
                sheduleFragment.setArguments(bundle);
                newTransaction.addToBackStack("week");
                newTransaction.commit();
            }
        });
        //привязка прослушивания нажатия на текстовое поле для изменения группы
        mEmptyStateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupChooseFragment groupChooseFragment = new GroupChooseFragment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.choose_group_view, groupChooseFragment, "choose_group");
                groupChooseFragment.setArguments(bundle);
                fragmentTransaction.addToBackStack("group");
                fragmentTransaction.commit();
            }
        });
    }

    //создание сетевого загрузчика
    @Override
    public Loader<List<Group>> onCreateLoader(int i, Bundle bundle) {
        return new SheduleLoader(this, MAI_REQUEST_URL);
    }

    //выполнение действий после завершения загрузки
    @Override
    public void onLoadFinished(Loader<List<Group>> loader, List<Group> groups) {
        mProgressBar.setVisibility(View.GONE);
        sPref = getSharedPreferences("sPref", Context.MODE_PRIVATE);
        //bundle = new Bundle();
        bundle.putParcelableArrayList("parced_info", (ArrayList<? extends Parcelable>) groups);
        if (sPref.contains("sPref")) {
            mButton.setVisibility(View.VISIBLE);
            mEmptyStateTextView.setText("Выбранная группа: " + sPref.getString("sPref", null));
        } else {
            // добавляем фрагмент
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            GroupChooseFragment groupChooseFragment = new GroupChooseFragment();
            fragmentTransaction.add(R.id.choose_group_view, groupChooseFragment, "choose_group");
            groupChooseFragment.setArguments(bundle);
            fragmentTransaction.addToBackStack("group");
            fragmentTransaction.commit();
        }
    }

    //возобновление загрузки
    @Override
    public void onLoaderReset(Loader<List<Group>> loader) {
    }
}