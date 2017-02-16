package ru.art.getyourweather.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
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
import ru.art.getyourweather.core.entity.Main;
import ru.art.getyourweather.core.entity.Weather;
import ru.art.getyourweather.core.entity.Wind;

/**
 * Created by nipol on 14.07.2016.
 */
public class DatabaseAccess extends SQLiteOpenHelper {

  private static final String TAG = "DatabaseAccess";

  private static final int DATABASE_VERSION = 1;
  private static final String DATABASE_NAME = "weatherDB.db";

  private static final String CREATE_TABLE_City = "CREATE TABLE City ("
        + "  id        integer NOT NULL PRIMARY KEY,"
        + "  name text,"
        + "  temp    real,"
        + "  pressure  real,"
        + "  humidity  real,"
        + "  lon       real,"
        + "  lat       real,"
        + "  speed     real"
        + ");";

  private static final String CREATE_TABLE_Weather = "CREATE TABLE Weather ("
        + "  id           integer NOT NULL PRIMARY KEY AUTOINCREMENT,"
        + "  description  text,"
        + "  city_id      integer NOT NULL,"
        + "  CONSTRAINT city_fk"
        + "    FOREIGN KEY (city_id)"
        + "    REFERENCES City(id)"
        + "    ON DELETE CASCADE"
        + ");";

  private static final String TABLE_CITY = "City";
  private static final String COLUMN_ID = "id";
  private static final String COLUMN_NAME = "name";
  private static final String COLUMN_TEMP = "temp";
  private static final String COLUMN_PRESSURE = "pressure";
  private static final String COLUMN_HUMIDITY = "humidity";
  private static final String COLUMN_LON = "lon";
  private static final String COLUMN_LAT = "lat";
  private static final String COLUMN_SPEED = "speed";

  private static final String TABLE_WEATHER = "Weather";
  private static final String COLUMN_DESCRIPTION = "description";
  private static final String COLUMN_CITY_ID = "city_id";

  private static DatabaseAccess sInstance;

  private DatabaseAccess(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  public static synchronized DatabaseAccess getInstance(Context context) {
    if (sInstance == null) {
      sInstance = new DatabaseAccess(context.getApplicationContext());
    }
    return sInstance;
  }

  @Override public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATE_TABLE_City);
    db.execSQL(CREATE_TABLE_Weather);
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITY);
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEATHER);
    onCreate(db);
  }

  public Observable<CityAggregation> syncDatabase(CityAggregation cityAggregation) {
    return Observable.create(new ObservableOnSubscribe<CityAggregation>() {
      @Override public void subscribe(ObservableEmitter<CityAggregation> subscriber) throws Exception {
        setCityAggregation(cityAggregation);
        subscriber.onNext(cityAggregation);
        subscriber.onComplete();
      }
    }).subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());
  }

  public void setCityAggregation(CityAggregation cityAggregation) {

    SQLiteDatabase db = getWritableDatabase();
    ContentValues cv = new ContentValues();
    db.beginTransaction();
    db.execSQL("DELETE FROM " + TABLE_WEATHER);

    try {
      for (City city : cityAggregation.cities) {
        cv.put(COLUMN_ID, city.getId());
        cv.put(COLUMN_NAME, city.getName());
        cv.put(COLUMN_TEMP, city.getMain().temp);
        cv.put(COLUMN_HUMIDITY, city.getMain().humidity);
        cv.put(COLUMN_PRESSURE, city.getMain().pressure);
        cv.put(COLUMN_LAT, city.getCoord().lat);
        cv.put(COLUMN_LON, city.getCoord().lon);
        cv.put(COLUMN_SPEED, city.getWind().speed);
        db.replace(TABLE_CITY, null, cv);

        for (Weather weather : city.getWeather()) {
          String query = "INSERT INTO " + TABLE_WEATHER + "(" + COLUMN_DESCRIPTION + ","
              + COLUMN_CITY_ID + ") " + "VALUES " + "('" + weather.description + "',"
              + "(SELECT id FROM City WHERE name='" + city.getName() + "'))";
          db.execSQL(query);
        }
      }
      db.setTransactionSuccessful();
    } catch (Exception e) {
      Log.d(TAG, "<--Database insert error-->");
    } finally {
      db.endTransaction();
    }
  }

  public Observable<CityAggregation> getCityAggregation() {
    SQLiteDatabase db = getReadableDatabase();
    List<City> cityList = new ArrayList<>();
    Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CITY, null);

    if (cursor.moveToFirst()) {
      do {
        City city = new City();
        city.setId(cursor.getLong(0));
        city.setName(cursor.getString(1));
        city.setMain(new Main());
        city.getMain().temp = cursor.getFloat(2);
        city.getMain().pressure = cursor.getFloat(3);
        city.getMain().humidity = cursor.getFloat(4);
        city.setCoord(new Coord());
        city.getCoord().lon = cursor.getFloat(5);
        city.getCoord().lat = cursor.getFloat(6);
        city.setWind(new Wind());
        city.getWind().speed = cursor.getFloat(7);

        Cursor wCursor = db.rawQuery("SELECT * FROM " + TABLE_WEATHER
            + " WHERE city_id='" + city.getId() + "'", null);

        if (wCursor.moveToFirst()) {
          city.setWeather(new Weather[wCursor.getCount()]);
          do {
            int count = 0;
              Weather weather = new Weather(wCursor.getLong(0), wCursor.getString(1));
              city.getWeather()[count] = weather;
            count++;
          } while (wCursor.moveToNext());
        }
        cityList.add(city);
      } while (cursor.moveToNext());
    }

    return Observable.create(new ObservableOnSubscribe<CityAggregation>() {
      @Override public void subscribe(ObservableEmitter<CityAggregation> subscriber) throws Exception {
        CityAggregation cityAggregation = new CityAggregation(cityList);
        subscriber.onNext(cityAggregation);
        subscriber.onComplete();
      }
    }).subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());
  }

}
