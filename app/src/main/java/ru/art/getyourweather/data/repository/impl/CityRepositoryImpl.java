package ru.art.getyourweather.data.repository.impl;

import android.content.Context;
import ru.art.getyourweather.core.aggregation.CityAggregation;
import ru.art.getyourweather.data.local.DatabaseAccess;
import ru.art.getyourweather.data.remote.WeatherService;
import ru.art.getyourweather.data.repository.CityRepository;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class CityRepositoryImpl implements CityRepository {

  private WeatherService mService;
  private DatabaseAccess databaseAccess;

  public CityRepositoryImpl(Context context) {
    mService = WeatherService.Builder.build();
    databaseAccess = DatabaseAccess.getInstance(context);
  }

  @Override public Observable<CityAggregation> getCitiesAggregation() {
    return databaseAccess.getCityAggregation();
  }

  @Override public Observable<CityAggregation> synchronize() {
    return mService.getCityAggregation()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .concatMap(cityAggregation -> databaseAccess.syncDatabase(cityAggregation));
  }
}