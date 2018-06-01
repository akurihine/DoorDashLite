package com.arkady.doordashlite.discover;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.arkady.doordashlite.R;
import com.arkady.doordashlite.data.model.Favorites;
import com.arkady.doordashlite.data.model.Restaurant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscoverView extends FrameLayout implements DiscoverContract.View {
    @BindView(R.id.list) DiscoverListView mListView;
    @BindView(R.id.error) View mError;
    @BindView(R.id.loading) View mLoading;

    private boolean mIsActive;
    private DiscoverContract.Presenter mPresenter;

    public DiscoverView(@NonNull Context context) {
        super(context);
        init();
    }

    public DiscoverView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DiscoverView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DiscoverView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_discover, this);
        ButterKnife.bind(this);
        mListView.setListener(new DiscoverListView.SearchResultsListener() {
            @Override
            public void onRestaurantClicked(android.view.View view, Restaurant restaurant) {
                mPresenter.onRestaurantSelected(restaurant);
            }

            @Override
            public void onFavoriteClicked(Restaurant restaurant) {
                mPresenter.onFavoriteClicked(restaurant);
            }
        });

        mIsActive = true;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mIsActive = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mIsActive = false;
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
        mListView.setFavorites(favorites);

    }

    @Override
    public boolean isActive() {
        return mIsActive;
    }
}
