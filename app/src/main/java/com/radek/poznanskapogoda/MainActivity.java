package com.radek.poznanskapogoda;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.radek.poznanskapogoda.adapters.WeatherAdapter;
import com.radek.poznanskapogoda.adapters.WeatherAdapterInterface;
import com.radek.poznanskapogoda.mvp.MainView;
import com.radek.poznanskapogoda.mvp.WeatherPresenter;
import com.radek.poznanskapogoda.mvp.WeatherPresenterImpl;
import com.radek.poznanskapogoda.network.WeatherAPI;
import com.radek.poznanskapogoda.network.models.WeatherListItem;
import com.radek.poznanskapogoda.network.response.WeatherResponse;

import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity implements MainView, WeatherAdapterInterface {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;
    @InjectView(R.id.content_main)
    RelativeLayout contentMain;
    @InjectView(R.id.fab)
    FloatingActionButton fab;
    @InjectView(R.id.progress_bar)
    ProgressBar progressBar;
    @InjectView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    private WeatherPresenter _presenter;
    private WeatherAPI _weatherAPI;
    private WeatherAdapter _weatherAdapter;

    private Callback<WeatherResponse> _weatherResponseCallback = new Callback<WeatherResponse>() {
        @Override
        public void onResponse(Response<WeatherResponse> response, Retrofit retrofit) {
            if (response != null) {
                _presenter.onDataReceive(response.body().getList());
            } else {
                Log.d("Error message", response.raw().message());
                Log.d("Error code", String.valueOf(response.raw().code()));
            }
        }

        @Override
        public void onFailure(Throwable t) {
            if (t instanceof IOException) {
                Log.d("Error message", "network connection error");
            } else {
                Log.d("Error message", "unknown connection error");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        initRetrofit();
        initView();
        _presenter = new WeatherPresenterImpl(this);
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BASE_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        _weatherAPI = retrofit.create(WeatherAPI.class);
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(null);
    }

    @OnClick(R.id.fab)
    public void fabClick() {
        _presenter.collectData();
    }

    @Override
    protected void onDestroy() {
        _presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void sendRequest() {
        Call<WeatherResponse> usersCall = _weatherAPI.getWeatherForPoznan();
        usersCall.enqueue(_weatherResponseCallback);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setAdapter(List<WeatherListItem> weatherList) {
        _weatherAdapter = new WeatherAdapter(weatherList, this);
        recyclerView.setAdapter(_weatherAdapter);
    }

    @Override
    public void showDetails(String details) {
        Snackbar.make(coordinatorLayout, details, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
