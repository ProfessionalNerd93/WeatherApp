package com.example.aarondelong.weatherapp.API_Calls.Google;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleGeoApi {

    @GET("json")
    Call<GoogleAddress> getAddress(@Query("address") String address, @Query("api_key") String apiKey);
}
