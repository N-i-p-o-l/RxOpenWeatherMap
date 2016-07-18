package ru.art.getyourweather.ui.citylist;

import android.util.Log;
import ru.art.getyourweather.core.aggregation.CityAggregation;
import ru.art.getyourweather.data.repository.CityRepository;
import ru.art.getyourweather.data.repository.impl.CityRepositoryImpl;
import rx.Subscriber;

public class CityListPresenter implements CityListContract.Presenter {

  private final static String TAG = "CityPresenter";

  private CityListContract.View mView;
  private CityRepository mRepository;
  private CityAggregation mAggregation;

  public CityListPresenter(CityListContract.View view, CityRepository cityRepository) {
    mView = view;
    mRepository = cityRepository;
  }

  @Override public CityAggregation onSaveInstanceState() {
    return mAggregation;
  }

  @Override public void onLoadInstanceState(CityAggregation aggregation) {
    this.mAggregation = aggregation;
  }

  @Override public void loadGuides() {
    mView.showLoadingLayout();
    mRepository.synchronize().subscribe(new Subscriber<CityAggregation>() {
      @Override public void onCompleted() {

      }

      @Override public void onError(Throwable e) {
        mView.showErrorLayout();
        Log.d(TAG, e.toString());
      }

      @Override public void onNext(CityAggregation cityAggregation) {
        CityListPresenter.this.mAggregation = cityAggregation;
        refreshUi();
      }
    });
  }

  @Override public void loadCacheGuides() {
    mView.showLoadingLayout();
    mRepository.getCitiesAggregation().subscribe(new Subscriber<CityAggregation>() {
      @Override public void onCompleted() {

      }

      @Override public void onError(Throwable e) {
        mView.showErrorLayout();
        Log.d(TAG, e.toString());
      }

      @Override public void onNext(CityAggregation cityAggregation) {
        CityListPresenter.this.mAggregation = cityAggregation;
        refreshUi();
      }
    });
  }

  @Override public void refreshUi() {
    if (mAggregation != null && mAggregation.cities.isEmpty()) {
      mView.showEmptyLayout();
    } else {
      mView.showSuccessLayout();
      mView.setupCityList(mAggregation.cities);
    }
  }

  @Override public void retryButtonClick() {
    loadGuides();
  }

}
