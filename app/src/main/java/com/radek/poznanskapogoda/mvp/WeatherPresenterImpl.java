package com.radek.poznanskapogoda.mvp;

import com.radek.poznanskapogoda.network.models.WeatherListItem;

import java.util.List;

/**
 * Created by Radek on 15.01.2017.
 */
public class WeatherPresenterImpl implements WeatherPresenter {

    private MainView _mainView;

    public WeatherPresenterImpl(MainView mainView) {
        _mainView = mainView;
        //// TODO: 15.01.2017 create Interactor
    }

    @Override
    public void collectData() {
        if (_mainView != null) {
            _mainView.sendRequest();
            _mainView.showProgress();
        }
    }

    @Override
    public void onDataReceive(List<WeatherListItem> weatherList) {
        if (_mainView != null) {
            _mainView.setAdapter(weatherList);
            _mainView.hideProgress();
        }
    }

    @Override
    public void onDestroy() {
        _mainView = null;
    }
}
