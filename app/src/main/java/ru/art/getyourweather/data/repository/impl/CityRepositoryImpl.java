package ru.art.getyourweather.data.repository.impl;

import android.app.Application;
import android.content.Context;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.art.getyourweather.App;
import ru.art.getyourweather.core.aggregation.CityAggregation;
import ru.art.getyourweather.data.local.OrmAccess;
import ru.art.getyourweather.data.remote.WeatherService;
import ru.art.getyourweather.data.repository.CityRepository;

public class CityRepositoryImpl implements CityRepository {

  private WeatherService mService;
  private OrmAccess ormAccess;

  public CityRepositoryImpl(App app) {
    mService = WeatherService.Builder.build();
    ormAccess = new OrmAccess(app.getDaoSession());
  }

  @Override public Observable<CityAggregation> getCitiesAggregation() {
    return ormAccess.getCityAggregation();
  }

  @Override public Observable<CityAggregation> synchronize() {
    return mService.getCityAggregation()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .concatMap(cityAggregation -> ormAccess.syncDatabase(cityAggregation));
  }
}
