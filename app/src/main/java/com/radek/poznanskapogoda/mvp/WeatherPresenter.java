package com.radek.poznanskapogoda.mvp;

import com.radek.poznanskapogoda.network.models.WeatherListItem;

import java.util.List;

/**
 * Created by Radek on 15.01.2017.
 */
public interface WeatherPresenter {

    void collectData();

    void onDataReceive(List<WeatherListItem> weatherList);

    void onDestroy();
}
