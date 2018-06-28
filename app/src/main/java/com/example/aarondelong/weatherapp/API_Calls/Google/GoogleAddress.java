package com.example.aarondelong.weatherapp.API_Calls.Google;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GoogleAddress {

    @SerializedName("results")
    private List<Results> results;

    public List<Results> getResults() {
        return results;
    }

    public class Results {

        @SerializedName("formatted_address")
        private String addressName;

        @SerializedName("geometry")
        private Geometry geometry;

        public String getAddressName() {
            return addressName;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public class Geometry {

            @SerializedName("location")
            private GoogleLocation location;

            public GoogleLocation getLocation() {
                return location;
            }

            public class GoogleLocation {

                @SerializedName("lat")
                private double lattitude;

                        @SerializedName("lng")
                private double longitude;

                public double getLattitude() {
                    return lattitude;
                }

                public double getLongitude() {
                    return longitude;
                }
            }
        }
    }
}
