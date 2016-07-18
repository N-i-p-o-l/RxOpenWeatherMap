package ru.art.getyourweather.core.entity;

import java.io.Serializable;

public class Weather implements Serializable {
  public transient Long id;
  public String description;

  public Weather(Long id, String description) {
    this.id = id;
    this.description = description;
  }
}
