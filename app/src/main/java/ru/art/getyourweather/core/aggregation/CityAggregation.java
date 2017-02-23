package ru.art.getyourweather.core.aggregation;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.reactivex.Observable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import ru.art.getyourweather.core.entity.City;

public class CityAggregation {

  public static final String KEY = "CityAggregation";

  public CityAggregation(List<City> cities) {
    this.cities = cities;
  }

  @SerializedName("list")
  @Expose
  public List<City> cities;
}
