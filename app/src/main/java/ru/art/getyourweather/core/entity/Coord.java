package ru.art.getyourweather.core.entity;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Coord {

  @Id(autoincrement = true)
  private Long id;

  @Expose
  private double lon;

  @Expose
  private double lat;

  @Generated(hash = 317593620)
  public Coord(Long id, double lon, double lat) {
      this.id = id;
      this.lon = lon;
      this.lat = lat;
  }
  @Generated(hash = 826419929)
  public Coord() {
  }
  public Long getId() {
      return this.id;
  }
  public void setId(Long id) {
      this.id = id;
  }
  public double getLon() {
      return this.lon;
  }
  public void setLon(double lon) {
      this.lon = lon;
  }
  public double getLat() {
      return this.lat;
  }
  public void setLat(double lat) {
      this.lat = lat;
  }
}
