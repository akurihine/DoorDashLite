package com.arkady.doordashlite.discover;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.arkady.doordashlite.R;
import com.arkady.doordashlite.data.model.Restaurant;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.security.PublicKey;

import butterknife.BindView;
import butterknife.ButterKnife;


class DiscoverItemView extends FrameLayout {
    @BindView(R.id.image) ImageView mImageView;
    @BindView(R.id.name) TextView mTitleView;
    @BindView(R.id.status) TextView mStatusView;
    @BindView(R.id.description) TextView mDescriptionView;
    @BindView(R.id.favorite) ImageView mFavorite;

    public Listener mListener;

    interface Listener {
        void onFavoriteClicked(Restaurant restaurant);
    }

    public DiscoverItemView(@NonNull Context context) {
        super(context);
        init();
    }

    public DiscoverItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DiscoverItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DiscoverItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_discover_list_item, this);
        ButterKnife.bind(this);
    }

    public void setRestaurant(Restaurant restaurant) {
        setRestaurant(restaurant, false);
    }

    public void setRestaurant(final Restaurant restaurant, boolean favorite) {
        Glide.with(getContext()).load(restaurant.getCoverImgUrl()).transition(DrawableTransitionOptions.withCrossFade()).into(mImageView);

        if (restaurant.getBusiness().getName() == null || restaurant.getBusiness().getName().trim().isEmpty()) {
            mTitleView.setVisibility(GONE);
        } else {
            mTitleView.setVisibility(VISIBLE);
        }

        mTitleView.setText(restaurant.getBusiness().getName());
        mStatusView.setText(restaurant.getStatus());
        mDescriptionView.setText(restaurant.getDescription());

        mFavorite.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) mListener.onFavoriteClicked(restaurant);
            }
        });

        setIsFavorite(favorite);
    }

    private void setIsFavorite(boolean isFavorite) {
        if (isFavorite) mFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);
        else mFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
    }

    public void setListener(Listener mListener) {
        this.mListener = mListener;
    }
}