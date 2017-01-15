package com.radek.poznanskapogoda.network;

import com.radek.poznanskapogoda.network.response.WeatherResponse;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by Radek on 15.01.2017.
 */

public interface WeatherAPI {

    @GET("/data/2.5/forecast/daily?q=Poznan&cnt=10&mode=json&appid=0d695b865bc37a0eed22b34854e62369")
    Call<WeatherResponse> getWeatherForPoznan();
}
