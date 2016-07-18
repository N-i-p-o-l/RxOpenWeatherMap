package ru.art.getyourweather.ui.citylist;

import java.util.List;
import ru.art.getyourweather.core.aggregation.CityAggregation;
import ru.art.getyourweather.core.entity.City;

public interface CityListContract {

  interface View {
    void setupCityList(List<City> cities);
    void showLoadingLayout();
    void showErrorLayout();
    void showSuccessLayout();
    void showEmptyLayout();
  }

  interface Presenter {
    CityAggregation onSaveInstanceState();
    void onLoadInstanceState(CityAggregation aggregation);
    void loadGuides();
    void loadCacheGuides();
    void refreshUi();
    void retryButtonClick();
  }

}
