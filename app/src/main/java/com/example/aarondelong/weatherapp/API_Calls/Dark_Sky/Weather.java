package com.example.aarondelong.weatherapp.API_Calls.Dark_Sky;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weather implements Parcelable {

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("currently")
    private CurrentWeather currentWeather;

    @SerializedName("daily")
    private DailyProperties dailyProperties;

    protected Weather(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<Weather> CREATOR = new Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel in) {
            return new Weather(in);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    public DailyProperties getDailyProperties() {
        return dailyProperties;
    }

    public class CurrentWeather {

        @SerializedName("summary")
        private String summary;

        @SerializedName("icon")
        private String icon;

        @SerializedName("temperature")
        private double temperature;

        public String getSummary() {
            return summary;
        }

        public String getIcon() {
            return icon;
        }

        public double getTemperature() {
            return temperature;
        }
    }

    public class DailyProperties {

        @SerializedName("data")
        private List<DailyData> dailyDataList;

        public List<DailyData> getDailyDataList() {
            return dailyDataList;
        }

        public class DailyData {

            @SerializedName("temperatureHigh")
            private double highTemp;

            @SerializedName("temperatureLow")
            private double lowTemp;

            @SerializedName("percipProbabilty")
            private double percipProbability;

            public double getHighTemp() {
                return highTemp;
            }

            public double getLowTemp() {
                return lowTemp;
            }

            public double getPercipProbability() {
                return percipProbability;
            }
        }
    }

}
