package ru.art.getyourweather.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import io.reactivex.Observable;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import ru.art.getyourweather.core.aggregation.CityAggregation;

public interface WeatherService {

  @GET("box/city?bbox=5,12,9,18,10&cluster=yes&units=metric&appid=e158b48e24a860e87f7c525df1a261a1")
  Observable<CityAggregation> getCityAggregation();

  class Builder {

    private static final String ENDPOINT = "http://api.openweathermap.org/data/2.5/";
    private static final int TIMEOUT = 60;
    private static final int WRITE_TIMEOUT = 120;
    private static final int CONNECT_TIMEOUT = 15;

    public static WeatherService build() {
      Gson lGson = new GsonBuilder()
          .excludeFieldsWithoutExposeAnnotation()
          .create();
      HttpLoggingInterceptor lInterceptor = new HttpLoggingInterceptor();

      OkHttpClient lClient = new OkHttpClient.Builder()
          .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
          .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
          .readTimeout(TIMEOUT, TimeUnit.SECONDS)
          .addInterceptor(lInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
          .build();
      Retrofit lRetrofit = new Retrofit.Builder()
          .baseUrl(ENDPOINT)
          .client(lClient)
          .addConverterFactory(GsonConverterFactory.create(lGson))
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .build();

      return lRetrofit.create(WeatherService.class);
    }

  }
}
