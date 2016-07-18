package ru.art.getyourweather.data.repository;

import ru.art.getyourweather.core.aggregation.CityAggregation;
import rx.Observable;

public interface CityRepository {

  Observable<CityAggregation> getCitiesAggregation();
  Observable<CityAggregation> synchronize();

}
