package ru.art.getyourweather.data.local;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import ru.art.getyourweather.core.aggregation.CityAggregation;
import ru.art.getyourweather.core.entity.City;
import ru.art.getyourweather.core.entity.Coord;
import ru.art.getyourweather.core.entity.DaoSession;
import ru.art.getyourweather.core.entity.Main;
import ru.art.getyourweather.core.entity.Weather;
import ru.art.getyourweather.core.entity.Wind;

public class OrmAccess {

  private DaoSession daoSession;

  public OrmAccess(DaoSession daoSession) {
    this.daoSession = daoSession;
  }

  public Observable<CityAggregation> syncDatabase(CityAggregation cityAggregation) {
    return Observable.create(new ObservableOnSubscribe<CityAggregation>() {
      @Override public void subscribe(ObservableEmitter<CityAggregation> subscriber) throws Exception {
        Observable.fromIterable(cityAggregation.cities).forEach(t -> {
          Long id = daoSession.getCoordDao().insertOrReplace(t.getCoordInMemory());
          t.setCoordId(id);

          id = daoSession.getMainDao().insertOrReplace(t.getMainInMemory());
          t.setMainId(id);

          id = daoSession.getWindDao().insertOrReplace(t.getWindInMemory());
          t.setWindId(id);

          Observable.fromIterable(t.getWeathersInMemory()).forEach(x -> {
            x.setWeatherId(t.getId());
            daoSession.getWeatherDao().insertOrReplace(x);
          });

          daoSession.getCityDao().insertOrReplace(t);
        });
        subscriber.onNext(cityAggregation);
        subscriber.onComplete();
      }
    }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public Observable<CityAggregation> getCityAggregation() {
    return Observable.create(new ObservableOnSubscribe<CityAggregation>() {
      @Override public void subscribe(ObservableEmitter<CityAggregation> subscriber) throws Exception {
        List<City> cityList = daoSession.getCityDao().queryBuilder().list();
        CityAggregation cityAggregation = new CityAggregation(cityList);
        subscriber.onNext(cityAggregation);
        subscriber.onComplete();
      }
    }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }
}
