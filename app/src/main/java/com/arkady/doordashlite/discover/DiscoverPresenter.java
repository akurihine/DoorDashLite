package com.arkady.doordashlite.discover;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.arkady.doordashlite.MyApplication;
import com.arkady.doordashlite.data.DataManager;
import com.arkady.doordashlite.data.model.Favorites;
import com.arkady.doordashlite.data.model.Restaurant;
import com.arkady.doordashlite.discover.DiscoverContract.Presenter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DiscoverPresenter implements Presenter {
    DataManager mDataManger;
    DiscoverContract.View mView;
    Favorites favorites;

    public DiscoverPresenter(DataManager dataManger, DiscoverContract.View view) {
        mDataManger = dataManger;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void loadRestaurants(boolean forceUpdate) {
        mView.setLoadingIndicator(true);
        mView.updateFavorites(getFavorites());

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

    @Override
    public void onFavoriteClicked(@NonNull Restaurant restaurant) {
        Favorites favorites = getFavorites();
        if (!favorites.getIds().contains(restaurant.getId())) {
            addToFavorite(restaurant);
        } else {
            removeFavorite(restaurant);
        }

        mView.updateFavorites(getFavorites());
    }

    private void removeFavorite(Restaurant restaurant) {
        Favorites favorites = getFavorites();
        List<Integer> idsToRemove = new ArrayList<>();
        for (Integer id : favorites.getIds()) {
            if (id.equals(restaurant.getId())) idsToRemove.add(id);
        }

        favorites.getIds().removeAll(idsToRemove);
        saveFavorites(favorites);
    }

    private void addToFavorite(Restaurant restaurant) {
        Favorites favorites = getFavorites();
        favorites.getIds().add(restaurant.getId());
        saveFavorites(favorites);
    }

    private void saveFavorites(Favorites favorites) {
        SharedPreferences sharedPref = MyApplication.getAppContext().getSharedPreferences("user1", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        editor.putString("favs", gson.toJson(favorites));
        editor.commit();
    }

    private Favorites getFavorites() {
        SharedPreferences sharedPref = MyApplication.getAppContext().getSharedPreferences("user1", Context.MODE_PRIVATE);
        String jsonString = sharedPref.getString("favs", "");
        Gson gson = new Gson();
        Favorites favorites = gson.fromJson(jsonString, Favorites.class);
        if (favorites == null) favorites = new Favorites(new ArrayList<Integer>());
        return favorites;
    }
}
