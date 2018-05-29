package com.arkady.doordashlite.discover;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.arkady.doordashlite.R;
import com.arkady.doordashlite.data.DataManagerImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscoverActivity extends AppCompatActivity {
    @BindView(R.id.discover) DiscoverView mDiscoverView;
    private DiscoverContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);
        ButterKnife.bind(this);
        mPresenter = new DiscoverPresenter(new DataManagerImpl(), mDiscoverView);
        mPresenter.loadRestaurants(true);
    }
}
