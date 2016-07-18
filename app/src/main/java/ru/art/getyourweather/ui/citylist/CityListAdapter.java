package ru.art.getyourweather.ui.citylist;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import ru.art.getyourweather.R;
import ru.art.getyourweather.core.entity.City;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ViewHolder> {

  private List<City> mCities;

  public CityListAdapter(List<City> cities) {
    mCities = cities;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
    return new ViewHolder(v);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    City lCity = mCities.get(position);
    if (lCity != null) {
      holder.cityName.setText(lCity.getName());
      holder.cityCoord.setText(lCity.getCoord().lat + " " + lCity.getCoord().lon);
      holder.cityWeather.setText(lCity.getWeather()[0].description);
      holder.cityTemp.setText(String.valueOf(lCity.getMain().temp));
    }
  }

  @Override public int getItemCount() {
    return mCities.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    TextView cityName, cityCoord,
             cityWeather, cityTemp;

    public ViewHolder(View itemView) {
      super(itemView);
      cityName = (TextView) itemView.findViewById(R.id.cityName);
      cityCoord = (TextView) itemView.findViewById(R.id.cityCoord);
      cityWeather = (TextView) itemView.findViewById(R.id.cityWeather);
      cityTemp = (TextView) itemView.findViewById(R.id.cityTemp);
    }

  }

}
