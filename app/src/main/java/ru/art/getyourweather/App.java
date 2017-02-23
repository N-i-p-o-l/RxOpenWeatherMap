package ru.art.getyourweather;

import android.app.Application;
import com.facebook.stetho.Stetho;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;
import ru.art.getyourweather.core.entity.DaoMaster;
import ru.art.getyourweather.core.entity.DaoMaster.DevOpenHelper;
import ru.art.getyourweather.core.entity.DaoSession;

public class App extends Application {

  private DaoSession daoSession;

  @Override public void onCreate() {
    super.onCreate();

    DevOpenHelper helper = new DevOpenHelper(this, "city-db");
    Database db = helper.getWritableDb();
    daoSession = new DaoMaster(db).newSession();

    if (BuildConfig.DEBUG) {
      QueryBuilder.LOG_SQL = true;
      QueryBuilder.LOG_VALUES = true;
      Stetho.initializeWithDefaults(this);
    }
  }

  public DaoSession getDaoSession() {
    return daoSession;
  }
}
