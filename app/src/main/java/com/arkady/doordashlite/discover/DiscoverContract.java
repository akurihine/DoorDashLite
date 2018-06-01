package com.arkady.doordashlite.discover;

import android.support.annotation.NonNull;

import com.arkady.doordashlite.data.model.Favorites;
import com.arkady.doordashlite.data.model.Restaurant;
import java.util.List;

public interface DiscoverContract {
    interface View {
        void setPresenter(Presenter presenter);
        void setLoadingIndicator(boolean active);
        void showRestaurants(List<Restaurant> restaurants);
        void showRestaurantDetails(Restaurant restaurant);
        void showLoadingRestaurantsError();
        void showNoRestaurants();
        void updateFavorites(Favorites favorites);
        boolean isActive();
    }

    interface Presenter {
        void loadRestaurants(boolean forceUpdate);
        void onRestaurantSelected(@NonNull Restaurant restaurant);
        void onFavoriteClicked(@NonNull Restaurant restaurant);
    }
}
