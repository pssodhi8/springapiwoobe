package com.woobe.model;

import java.io.Serializable;

public class Rating implements Serializable {
  private static final long serialVersionUID = 7119611258813219016L;
  int id;
  int badRatings;
  int completedJobs;
  int goodRatings;
  int grayRatings;
  float rating;
  String user_id;
  String user;
  public float getRating() {
    return rating;
  }
  public void setRating(float rating) {
    this.rating = rating;
  }
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public int getBadRatings() {
    return badRatings;
  }
  public void setBadRatings(int badRatings) {
    this.badRatings = badRatings;
  }
  public int getCompletedJobs() {
    return completedJobs;
  }
  public void setCompletedJobs(int completedJobs) {
    this.completedJobs = completedJobs;
  }
  public int getGoodRatings() {
    return goodRatings;
  }
  public void setGoodRatings(int goodRatings) {
    this.goodRatings = goodRatings;
  }
  public int getGrayRatings() {
    return grayRatings;
  }
  public void setGrayRatings(int grayRatings) {
    this.grayRatings = grayRatings;
  }

  public String getUser_id() {
    return user_id;
  }
  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }
  public String getUser() {
    return user;
  }
  public void setUser(String user) {
    this.user = user;
  }
}
