package ru.art.getyourweather.data.repository;

import io.reactivex.Observable;
import ru.art.getyourweather.core.aggregation.CityAggregation;

public interface CityRepository {

  Observable<CityAggregation> getCitiesAggregation();
  Observable<CityAggregation> synchronize();
}
