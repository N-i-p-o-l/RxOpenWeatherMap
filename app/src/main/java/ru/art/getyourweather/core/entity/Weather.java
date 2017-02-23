package ru.art.getyourweather.core.entity;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Weather {

  @Id(autoincrement = true)
  private Long id;

  private long weatherId;

  @Expose
  private String description;

  @Generated(hash = 1297175088)
  public Weather(Long id, long weatherId, String description) {
      this.id = id;
      this.weatherId = weatherId;
      this.description = description;
  }
  @Generated(hash = 556711069)
  public Weather() {
  }
  public Long getId() {
      return this.id;
  }
  public void setId(Long id) {
      this.id = id;
  }
  public long getWeatherId() {
      return this.weatherId;
  }
  public void setWeatherId(long weatherId) {
      this.weatherId = weatherId;
  }
  public String getDescription() {
      return this.description;
  }
  public void setDescription(String description) {
      this.description = description;
  }
}
