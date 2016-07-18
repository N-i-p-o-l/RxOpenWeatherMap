package ru.art.getyourweather.ui.citylist;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import java.util.List;
import ru.art.getyourweather.R;
import ru.art.getyourweather.core.aggregation.CityAggregation;
import ru.art.getyourweather.core.entity.City;
import ru.art.getyourweather.data.remote.WeatherService;
import ru.art.getyourweather.data.repository.CityRepository;
import ru.art.getyourweather.data.repository.impl.CityRepositoryImpl;
import ru.art.getyourweather.ui.base.BaseActivity;

public class CityListActivity extends BaseActivity implements CityListContract.View {

  private RecyclerView cityList;
  private LinearLayout successContainer;
  private LinearLayout errorContainer;
  private LinearLayout loadingContainer;
  private LinearLayout emptyContainer;
  private Button retryButton;

  private CityListContract.Presenter mPresenter;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_city_list);

    cityList = (RecyclerView) findViewById(R.id.cityListRecycleView);
    successContainer = (LinearLayout) findViewById(R.id.successContainer);
    errorContainer = (LinearLayout) findViewById(R.id.errorContainer);
    loadingContainer = (LinearLayout) findViewById(R.id.loadingContainer);
    emptyContainer = (LinearLayout) findViewById(R.id.emptyContainer);
    retryButton = (Button) findViewById(R.id.retryButton);

    dependencyInjection();
    initialize(savedInstanceState);

    retryButton.setOnClickListener(v -> mPresenter.retryButtonClick());
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    outState.putSerializable(CityAggregation.KEY, mPresenter.onSaveInstanceState());
    super.onSaveInstanceState(outState);
  }

  private void initialize(Bundle bundle) {
    if (bundle != null && bundle.containsKey(CityAggregation.KEY)) {
      mPresenter.onLoadInstanceState((CityAggregation) bundle.getSerializable(CityAggregation.KEY));
      mPresenter.refreshUi();
    } else if (isConnected()){
      mPresenter.loadGuides();
    } else {mPresenter.loadCacheGuides();}
  }

  private void dependencyInjection() {
    CityRepository lRepository = new CityRepositoryImpl(getApplicationContext());
    mPresenter = new CityListPresenter(this, lRepository);
  }

  @Override public void setupCityList(List<City> cities) {
    LinearLayoutManager manager = new LinearLayoutManager(this);
    CityListAdapter lCityListAdapter = new CityListAdapter(cities);
    cityList.setLayoutManager(manager);
    cityList.setAdapter(lCityListAdapter);
  }

  @Override public void showLoadingLayout() {
    successContainer.setVisibility(View.GONE);
    errorContainer.setVisibility(View.GONE);
    loadingContainer.setVisibility(View.VISIBLE);
    emptyContainer.setVisibility(View.GONE);
  }

  @Override public void showErrorLayout() {
    successContainer.setVisibility(View.GONE);
    errorContainer.setVisibility(View.VISIBLE);
    loadingContainer.setVisibility(View.GONE);
    emptyContainer.setVisibility(View.GONE);
  }

  @Override public void showSuccessLayout() {
    successContainer.setVisibility(View.VISIBLE);
    errorContainer.setVisibility(View.GONE);
    loadingContainer.setVisibility(View.GONE);
    emptyContainer.setVisibility(View.GONE);
  }

  @Override public void showEmptyLayout() {
    successContainer.setVisibility(View.GONE);
    errorContainer.setVisibility(View.GONE);
    loadingContainer.setVisibility(View.GONE);
    emptyContainer.setVisibility(View.VISIBLE);
  }

}
