package com.arkady.doordashlite.discover;

import android.support.annotation.NonNull;

import com.arkady.doordashlite.data.DataManager;
import com.arkady.doordashlite.data.model.Restaurant;
import com.arkady.doordashlite.discover.DiscoverContract.Presenter;

import java.util.List;

public class DiscoverPresenter implements Presenter {
    DataManager mDataManger;
    DiscoverContract.View mView;

    public DiscoverPresenter(DataManager dataManger, DiscoverContract.View view) {
        mDataManger = dataManger;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void loadRestaurants(boolean forceUpdate) {
        mView.setLoadingIndicator(true);

        mDataManger.getRestaurants(new DataManager.LoadRestaurantsCallback() {
            @Override
            public void onRestaurantsLoaded(List<Restaurant> restaurants) {
                if (mView.isActive()) {
                    mView.setLoadingIndicator(false);
                    if (restaurants.isEmpty()) {
                        mView.showNoRestaurants();
                    } else {
                        mView.showRestaurants(restaurants);
                    }
                }
            }

            @Override
            public void onDataNotAvailable() {
                if (mView.isActive()) {
                    mView.setLoadingIndicator(false);
                    mView.showLoadingRestaurantsError();
                }
            }
        });
    }

    @Override
    public void onRestaurantSelected(@NonNull Restaurant restaurant) {
        mView.showRestaurantDetails(restaurant);
    }
}
