package com.radek.poznanskapogoda.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.radek.poznanskapogoda.R;
import com.radek.poznanskapogoda.helpers.WeatherTools;
import com.radek.poznanskapogoda.network.models.WeatherListItem;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Radek on 15.01.2017.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private List<WeatherListItem> _values;
    private WeatherAdapterInterface _adapterInterface;
    private Context _context;

    static class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.date)
        TextView date;
        @InjectView(R.id.temp)
        TextView temp;
        @InjectView(R.id.pressure)
        TextView pressure;
        @InjectView(R.id.humidity)
        TextView humidity;
        @InjectView(R.id.weather)
        TextView weather;
        @InjectView(R.id.card_view)
        CardView cardView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

    public WeatherAdapter(List<WeatherListItem> values, Context context) {
        _values = values;
        _context = context;
        try {
            _adapterInterface = (WeatherAdapterInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("context must implement WeatherAdapterInterface");
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.date.setText(WeatherTools.getDate(_values.get(position).getDt()));
        holder.temp.setText(_context.getString(R.string.adapter_row_temp, _values.get(position).getTemp().getMax()));
        holder.pressure.setText(_context.getString(R.string.adapter_row_pressure, _values.get(position).getPressure()));
        holder.humidity.setText(_context.getString(R.string.adapter_row_humidity, _values.get(position).getHumidity()));
        holder.weather.setText(_values.get(position).getWeather().get(0).getDescription());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //// TODO: 15.01.2017 make lisdtener as private class
                _adapterInterface.showDetails(_values.get(holder.getAdapterPosition()).getWeather().get(0).getMain());
            }
        });
    }

    @Override
    public int getItemCount() {
        return _values.size();
    }
}