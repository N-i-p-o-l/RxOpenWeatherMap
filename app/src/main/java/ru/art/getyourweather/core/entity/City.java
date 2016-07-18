package ru.art.getyourweather.core.entity;

import java.io.Serializable;

public class City implements Serializable {
  private Long id;
  private String name;
  private Coord coord;
  private Main main;
  private Wind wind;
  private Weather[] weather;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Weather[] getWeather() {
    return weather;
  }

  public void setWeather(Weather[] weather) {
    this.weather = weather;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Coord getCoord() {
    return coord;
  }

  public void setCoord(Coord coord) {
    this.coord = coord;
  }

  public Main getMain() {
    return main;
  }

  public void setMain(Main main) {
    this.main = main;
  }

  public Wind getWind() {
    return wind;
  }

  public void setWind(Wind wind) {
    this.wind = wind;
  }
}
