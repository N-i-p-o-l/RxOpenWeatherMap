package ru.art.getyourweather.ui.citylist;

import android.util.Log;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import ru.art.getyourweather.core.aggregation.CityAggregation;
import ru.art.getyourweather.data.repository.CityRepository;

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
    mRepository.synchronize().subscribe(new Observer<CityAggregation>() {
      @Override public void onSubscribe(Disposable d) {
      }

      @Override public void onNext(CityAggregation cityAggregation) {
        CityListPresenter.this.mAggregation = cityAggregation;
        refreshUi();
      }

      @Override public void onError(Throwable e) {
        mView.showErrorLayout();
        Log.d(TAG, e.toString());
      }

      @Override public void onComplete() {
      }
    });
  }

  @Override public void loadCacheGuides() {
    mView.showLoadingLayout();
    mRepository.getCitiesAggregation().subscribe(new Observer<CityAggregation>() {
      @Override public void onSubscribe(Disposable d) {
      }

      @Override public void onNext(CityAggregation cityAggregation) {
        CityListPresenter.this.mAggregation = cityAggregation;
        refreshUi();
      }

      @Override public void onError(Throwable e) {
        mView.showErrorLayout();
        Log.d(TAG, e.toString());
      }

      @Override public void onComplete() {
      }
    });
  }

  @Override public void refreshUi() {
    if (mAggregation != null) {
      if (mAggregation.cities.isEmpty()) {
        mView.showEmptyLayout();
      } else {
          mView.showSuccessLayout();
          mView.setupCityList(mAggregation.cities);
      }
    }
  }

  @Override public void retryButtonClick() {
    loadGuides();
  }

}
