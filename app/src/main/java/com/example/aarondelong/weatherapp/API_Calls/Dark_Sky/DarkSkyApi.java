package com.example.aarondelong.weatherapp.API_Calls.Dark_Sky;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DarkSkyApi {

    @GET("{api_key}/{latitude},{longitude}")
    Call<Weather> getWeather (@Path("api_key") String apiKey, @Path("latitude") double latitude, @Path("longitude") double longitude);


}
