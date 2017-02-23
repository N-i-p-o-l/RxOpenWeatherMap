package ru.art.getyourweather.core.entity;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Main {

  @Id(autoincrement = true)
  private Long id;

  @Expose
  private float temp;

  @Expose
  private float pressure;

  @Expose
  private float humidity;

  @Generated(hash = 1471386243)
  public Main(Long id, float temp, float pressure, float humidity) {
      this.id = id;
      this.temp = temp;
      this.pressure = pressure;
      this.humidity = humidity;
  }
  @Generated(hash = 1298277417)
  public Main() {
  }
  public Long getId() {
      return this.id;
  }
  public void setId(Long id) {
      this.id = id;
  }
  public float getTemp() {
      return this.temp;
  }
  public void setTemp(float temp) {
      this.temp = temp;
  }
  public float getPressure() {
      return this.pressure;
  }
  public void setPressure(float pressure) {
      this.pressure = pressure;
  }
  public float getHumidity() {
      return this.humidity;
  }
  public void setHumidity(float humidity) {
      this.humidity = humidity;
  }
}
