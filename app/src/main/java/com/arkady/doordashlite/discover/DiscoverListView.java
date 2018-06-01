package com.arkady.doordashlite.discover;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.arkady.doordashlite.R;
import com.arkady.doordashlite.data.model.Favorites;
import com.arkady.doordashlite.data.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscoverListView extends FrameLayout{
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    private SearchResultsListener mListener;
    private DiscoverRecyclerViewAdapter mAdapter;
    private List<Restaurant> mRestaurantList;
    private Favorites mFavorites = new Favorites(new ArrayList<Integer>());

    public interface SearchResultsListener {
        void onRestaurantClicked(View view, Restaurant restaurant);
        void onFavoriteClicked(Restaurant restaurant);
    }

    public DiscoverListView(@NonNull Context context) {
        super(context);
        init();
    }

    public DiscoverListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DiscoverListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DiscoverListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_discover_list, this);
        ButterKnife.bind(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void setData(List<Restaurant> restaurantList) {
        setData(restaurantList, true);
    }

    public void setData(List<Restaurant> restaurantList, boolean isAllNewData) {
        mRestaurantList = restaurantList;

        if (isAllNewData) {
            if (mAdapter == null) {
                mAdapter = new DiscoverRecyclerViewAdapter();
                mRecyclerView.setAdapter(mAdapter);
            } else {
                mRecyclerView.scrollToPosition(0);
                mAdapter.notifyDataSetChanged();
            }
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    public void setFavorites(Favorites favorites) {
        if (favorites != null) {
            mFavorites = favorites;
        } else {
            mFavorites = new Favorites(new ArrayList<Integer>());
        }

        if (mAdapter != null) mAdapter.notifyDataSetChanged();
    }

    public void setListener(SearchResultsListener listener) {
        mListener = listener;
    }


    private class DiscoverRecyclerViewAdapter extends RecyclerView.Adapter<DiscoverRecyclerViewAdapter.RestaurantViewHolder> {
        @Override
        public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            DiscoverItemView discoverItemView = new DiscoverItemView(parent.getContext());
            discoverItemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            RestaurantViewHolder holder = new RestaurantViewHolder(discoverItemView);
            return holder;
        }

        @Override
        public void onBindViewHolder(RestaurantViewHolder holder, int position) {
            holder.discoverItemView.setRestaurant(mRestaurantList.get(position), mFavorites.getIds().contains(mRestaurantList.get(position).getId()));
        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        @Override
        public int getItemCount() {
            return mRestaurantList.size();
        }

        public class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public DiscoverItemView discoverItemView;

            public RestaurantViewHolder(DiscoverItemView itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                itemView.setListener(new DiscoverItemView.Listener() {
                    @Override
                    public void onFavoriteClicked(Restaurant restaurant) {
                        mListener.onFavoriteClicked(restaurant);
                    }
                });
                discoverItemView = itemView;
            }

            @Override
            public void onClick(View view) {
                if (mListener != null) mListener.onRestaurantClicked(view, mRestaurantList.get(getPosition()));
            }
        }
    }
}

