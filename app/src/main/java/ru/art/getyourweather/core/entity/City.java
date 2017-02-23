package ru.art.getyourweather.core.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity
public class City {

  @Id @Expose
  private Long id;

  @Expose
  private String name;

  private Long coordId;

  @ToOne(joinProperty = "coordId")
  @Expose
  private Coord coord;

  private Long mainId;

  @ToOne(joinProperty = "mainId")
  @Expose
  private Main main;

  private Long windId;

  @ToOne(joinProperty = "windId")
  @Expose
  private Wind wind;

  @ToMany(referencedJoinProperty = "weatherId")
  @SerializedName("weather")
  @Expose
  private List<Weather> weathers;

  /*@Transient @Expose
  public Weather[] weather;*/

  /** Used to resolve relations */
  @Generated(hash = 2040040024)
  private transient DaoSession daoSession;

  /** Used for active entity operations. */
  @Generated(hash = 448079911)
  private transient CityDao myDao;

  @Generated(hash = 31497840)
public City(Long id, String name, Long coordId, Long mainId, Long windId) {
    this.id = id;
    this.name = name;
    this.coordId = coordId;
    this.mainId = mainId;
    this.windId = windId;
}

@Generated(hash = 750791287)
  public City() {
  }

  public Long getId() {
      return this.id;
  }

  public void setId(Long id) {
      this.id = id;
  }

  public String getName() {
      return this.name;
  }

  public void setName(String name) {
      this.name = name;
  }

  /** To-one relationship, resolved on first access. */
@Generated(hash = 817309276)
public Coord getCoord() {
    Long __key = this.coordId;
    if (coord__resolvedKey == null || !coord__resolvedKey.equals(__key)) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        CoordDao targetDao = daoSession.getCoordDao();
        Coord coordNew = targetDao.load(__key);
        synchronized (this) {
            coord = coordNew;
            coord__resolvedKey = __key;
        }
    }
    return coord;
}

public Coord getCoordInMemory() {
    return coord;
  }

  /** called by internal mechanisms, do not call yourself. */
@Generated(hash = 1171965729)
public void setCoord(Coord coord) {
    synchronized (this) {
        this.coord = coord;
        coordId = coord == null ? null : coord.getId();
        coord__resolvedKey = coordId;
    }
}

/** To-one relationship, resolved on first access. */
@Generated(hash = 118003886)
public Main getMain() {
    Long __key = this.mainId;
    if (main__resolvedKey == null || !main__resolvedKey.equals(__key)) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        MainDao targetDao = daoSession.getMainDao();
        Main mainNew = targetDao.load(__key);
        synchronized (this) {
            main = mainNew;
            main__resolvedKey = __key;
        }
    }
    return main;
}

public Main getMainInMemory() {
    return main;
  }

  /** called by internal mechanisms, do not call yourself. */
@Generated(hash = 2081951190)
public void setMain(Main main) {
    synchronized (this) {
        this.main = main;
        mainId = main == null ? null : main.getId();
        main__resolvedKey = mainId;
    }
}

@Generated(hash = 439018334)
private transient Long coord__resolvedKey;

@Generated(hash = 352896832)
private transient Long main__resolvedKey;

@Generated(hash = 686833448)
private transient Long wind__resolvedKey;

/** To-one relationship, resolved on first access. */
@Generated(hash = 473687079)
public Wind getWind() {
    Long __key = this.windId;
    if (wind__resolvedKey == null || !wind__resolvedKey.equals(__key)) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        WindDao targetDao = daoSession.getWindDao();
        Wind windNew = targetDao.load(__key);
        synchronized (this) {
            wind = windNew;
            wind__resolvedKey = __key;
        }
    }
    return wind;
}

public Wind getWindInMemory() {
    return wind;
  }

  /** called by internal mechanisms, do not call yourself. */
@Generated(hash = 821355519)
public void setWind(Wind wind) {
    synchronized (this) {
        this.wind = wind;
        windId = wind == null ? null : wind.getId();
        wind__resolvedKey = windId;
    }
}

/**
   * To-many relationship, resolved on first access (and after reset).
   * Changes to to-many relations are not persisted, make changes to the target entity.
   */
  @Generated(hash = 1431226900)
  public List<Weather> getWeathers() {
      if (weathers == null) {
          final DaoSession daoSession = this.daoSession;
          if (daoSession == null) {
              throw new DaoException("Entity is detached from DAO context");
          }
          WeatherDao targetDao = daoSession.getWeatherDao();
          List<Weather> weathersNew = targetDao._queryCity_Weathers(id);
          synchronized (this) {
              if (weathers == null) {
                  weathers = weathersNew;
              }
          }
      }
      return weathers;
  }

  public List<Weather> getWeathersInMemory() {
    return weathers;
  }

  /** Resets a to-many relationship, making the next get call to query for a fresh result. */
  @Generated(hash = 883555496)
  public synchronized void resetWeathers() {
      weathers = null;
  }

  /**
   * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
   * Entity must attached to an entity context.
   */
  @Generated(hash = 128553479)
  public void delete() {
      if (myDao == null) {
          throw new DaoException("Entity is detached from DAO context");
      }
      myDao.delete(this);
  }

  /**
   * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
   * Entity must attached to an entity context.
   */
  @Generated(hash = 1942392019)
  public void refresh() {
      if (myDao == null) {
          throw new DaoException("Entity is detached from DAO context");
      }
      myDao.refresh(this);
  }

  /**
   * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
   * Entity must attached to an entity context.
   */
  @Generated(hash = 713229351)
  public void update() {
      if (myDao == null) {
          throw new DaoException("Entity is detached from DAO context");
      }
      myDao.update(this);
  }

  public Long getCoordId() {
    return this.coordId;
}

public void setCoordId(Long coordId) {
    this.coordId = coordId;
}

public Long getMainId() {
    return this.mainId;
}

public void setMainId(Long mainId) {
    this.mainId = mainId;
}

public Long getWindId() {
    return this.windId;
}

public void setWindId(Long windId) {
    this.windId = windId;
}

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 293508440)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getCityDao() : null;
}
}
