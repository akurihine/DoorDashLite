package com.arkady.doordashlite.data;

import android.support.annotation.NonNull;

import com.arkady.doordashlite.data.model.Restaurant;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataManagerImpl implements DataManager {

    final Retrofit restAdapter = buildRestAdapter();

    @Override
    public void getRestaurants(@NonNull final LoadRestaurantsCallback callback) {
        restAdapter.create(DoorDashApi.class).getRestaurants(37.422740, -122.139956).enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                if (response.body() != null) callback.onRestaurantsLoaded(response.body());
                else callback.onDataNotAvailable();
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getRestaurant(@NonNull String restaurantId, @NonNull final GetRestaurantCallback callback) {
        restAdapter.create(DoorDashApi.class).getRestaurant(restaurantId).enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                if (response.body() != null) callback.onRestaurantLoaded(response.body());
                else callback.onDataNotAvailable();
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    private Retrofit buildRestAdapter() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit doorDashAdapter = new Retrofit.Builder()
                .baseUrl(DoorDashApi.SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return doorDashAdapter;
    }
}
