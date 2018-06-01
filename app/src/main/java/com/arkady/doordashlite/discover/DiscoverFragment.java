package com.arkady.doordashlite.discover;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arkady.doordashlite.R;
import com.arkady.doordashlite.data.model.Favorites;
import com.arkady.doordashlite.data.model.Restaurant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * NOT USED but could be an alternative to a custom view
 */
public class DiscoverFragment extends Fragment implements DiscoverContract.View {
    @BindView(R.id.list) DiscoverListView mListView;
    @BindView(R.id.error) View mError;
    @BindView(R.id.loading) View mLoading;

    private boolean mIsActive;
    private DiscoverContract.Presenter mPresenter;

    public static DiscoverFragment newInstance() {
        return new DiscoverFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_discover, container, false);
        ButterKnife.bind(this, view);
        mListView.setListener(new DiscoverListView.SearchResultsListener() {
            @Override
            public void onRestaurantClicked(android.view.View view, Restaurant restaurant) {
                mPresenter.onRestaurantSelected(restaurant);
            }

            @Override
            public void onFavoriteClicked(Restaurant restaurant) {

            }
        });

        mIsActive = true;
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mIsActive = true;
        mPresenter.loadRestaurants(false);
    }

    @Override
    public void setPresenter(DiscoverContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if(active) mLoading.setVisibility(VISIBLE);
        else mLoading.setVisibility(GONE);
    }

    @Override
    public void showRestaurants(List<Restaurant> restaurants) {
        mListView.setData(restaurants);
    }

    @Override
    public void showRestaurantDetails(Restaurant restaurant) {
        //do nothing
    }

    @Override
    public void showLoadingRestaurantsError() {
        mError.setVisibility(VISIBLE);
    }

    @Override
    public void showNoRestaurants() {
        mError.setVisibility(VISIBLE);
    }

    @Override
    public void updateFavorites(Favorites favorites) {

    }

    @Override
    public boolean isActive() {
        return mIsActive;
    }
}
