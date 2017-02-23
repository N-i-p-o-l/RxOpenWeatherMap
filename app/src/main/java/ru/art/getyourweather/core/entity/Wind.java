package ru.art.getyourweather.core.entity;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Wind {

  @Id(autoincrement = true)
  private Long id;

  @Expose
  private float speed;

  @Generated(hash = 1696032685)
  public Wind(Long id, float speed) {
      this.id = id;
      this.speed = speed;
  }

  @Generated(hash = 1286276427)
  public Wind() {
  }

  public Long getId() {
      return this.id;
  }

  public void setId(Long id) {
      this.id = id;
  }

  public float getSpeed() {
      return this.speed;
  }

  public void setSpeed(float speed) {
      this.speed = speed;
  }
}
