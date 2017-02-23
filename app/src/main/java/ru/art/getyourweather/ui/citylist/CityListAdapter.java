package ru.art.getyourweather.ui.citylist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import ru.art.getyourweather.R;
import ru.art.getyourweather.core.entity.City;
import ru.art.getyourweather.core.entity.Coord;
import ru.art.getyourweather.core.entity.Main;
import ru.art.getyourweather.core.entity.Weather;

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
    City city = mCities.get(position);
    if (city != null) {
      Coord coord = city.getCoord();
      Main main = city.getMain();
      List<Weather> weathers = city.getWeathers();
      holder.cityName.setText(city.getName());
      holder.cityCoord.setText(coord.getLat() + " " + coord.getLon());
      holder.cityWeather.setText(weathers.get(0).getDescription());
      holder.cityTemp.setText(String.valueOf(main.getTemp()));
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
