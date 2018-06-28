package com.example.aarondelong.weatherapp;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.aarondelong.weatherapp.API_Calls.Dark_Sky.DarkSkyApi;
import com.example.aarondelong.weatherapp.API_Calls.Dark_Sky.Weather;
import com.example.aarondelong.weatherapp.API_Calls.Google.GoogleAddress;
import com.example.aarondelong.weatherapp.API_Calls.Google.GoogleGeoApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private GoogleGeoApi googleGeoApi;
    private DarkSkyApi darkSkyApi;
    private String googleBaseUrl;
    private String darkSkyBaseUrl;
    private String googleApiKey;
    private String darkSkyApiKey;
    private Retrofit googleRetrofit;
    private Retrofit darkSkyRetrofit;

    @BindView(R.id.location_edittext)
    protected TextInputEditText location;

    private Bundle bundle;

    public static final String PLACE = "place";
    public static final String WEATHER = "weather";
    private WeatherFragment weatherFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bundle = new Bundle();
        weatherFragment = WeatherFragment.newInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        googleBaseUrl = getString(R.string.google_base_url);
        darkSkyBaseUrl = getString(R.string.dark_sky_base_url);
        googleApiKey = getString(R.string.google_api_key);
        darkSkyApiKey = getString(R.string.dark_sky_api_key);

        googleGeoApi = getGoogleRetrofit().create(GoogleGeoApi.class);
        darkSkyApi = getDarkSkyRetrofit().create(DarkSkyApi.class);

    }

    private Retrofit getGoogleRetrofit() {
        if (googleRetrofit == null) {

            googleRetrofit = new Retrofit.Builder()
                    .baseUrl(googleBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return googleRetrofit;

    }

    private Retrofit getDarkSkyRetrofit() {

        if (darkSkyRetrofit == null) {

            darkSkyRetrofit = new Retrofit.Builder()
                    .baseUrl(darkSkyBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return darkSkyRetrofit;

    }

    @OnClick(R.id.submit_button)
    protected void submitClicked() {

        if (location.getText().toString().isEmpty()) {
            Toast.makeText(this, "You need to enter a location!", Toast.LENGTH_SHORT).show();
        } else {


            getLocation(location.getText().toString());
        }
    }

    private void getLocation(String address) {
        googleGeoApi.getAddress(address, googleApiKey).enqueue(new Callback<GoogleAddress>() {
            @Override
            public void onResponse(Call<GoogleAddress> call, Response<GoogleAddress> response) {
                try {
                    if (response.isSuccessful()) {

                        bundle.putString(PLACE, response.body().getResults().get(0).getAddressName());

                        getWeather(response.body().getResults().get(0).getGeometry().getLocation().getLattitude(), response.body().getResults().get(0).getGeometry().getLocation().getLongitude());

                    } else {
                        Toast.makeText(MainActivity.this, "Call was made, but unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Try again, google call threw exception", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GoogleAddress> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Google Call failed!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void getWeather(double lat, double lng) {

        darkSkyApi.getWeather(darkSkyApiKey, lat, lng).enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                try {
                    if (response.isSuccessful()) {

                        bundle.putParcelable(WEATHER, response.body());
//                    Send bundle to fragment. Create method to handle transition to fragment.
                        weatherFragment.setArguments(bundle);
                        transitionToWeatherFragment();

                    } else {
                        Toast.makeText(MainActivity.this, "Weather call made but unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Try again, google call threw exception", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Weather Call failed!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void transitionToWeatherFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, weatherFragment).commit();

    }

    @Override
    public void onBackPressed() {
        if (weatherFragment.isVisible()) {
            getSupportFragmentManager().beginTransaction().remove(weatherFragment).commit();

        } else {
            super.onBackPressed();
        }
    }
}
