package com.arkady.doordashlite.data;

import android.support.annotation.NonNull;

import com.arkady.doordashlite.data.model.Restaurant;

import java.util.List;

public interface DataManager {
    interface LoadRestaurantsCallback {
        void onRestaurantsLoaded(List<Restaurant> restaurants);
        void onDataNotAvailable();
    }

    interface GetRestaurantCallback {
        void onRestaurantLoaded(Restaurant restaurant);
        void onDataNotAvailable();
    }

    public void getRestaurants(@NonNull LoadRestaurantsCallback callback);
    public void getRestaurant(@NonNull String taskId, @NonNull GetRestaurantCallback callback);
}
