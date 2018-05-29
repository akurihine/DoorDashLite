package com.arkady.doordashlite.data;

import com.arkady.doordashlite.data.model.Restaurant;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DoorDashApi {
    String SERVER = " https://api.doordash.com/v2/restaurant/";

    @GET("/v2/restaurant/")
    Call<List<Restaurant>> getRestaurants(
            @Query("lat") Double lat,
            @Query("lng") Double lng
    );

    @GET("/v2/restaurant/{id}/")
    Call<Restaurant> getRestaurant(@Path("id") String id);
}
