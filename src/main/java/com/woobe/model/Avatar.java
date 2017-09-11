package com.woobe.model;

import java.io.Serializable;

public class Avatar implements Serializable{
  private static final long serialVersionUID = -4464492070889845807L;
  int id;
  String filename;
  int user_id;
  int hide;
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getFilename() {
    return filename;
  }
  public void setFilename(String filename) {
    this.filename = filename;
  }
  public int getUser_id() {
    return user_id;
  }
  public void setUser_id(int user_id) {
    this.user_id = user_id;
  }
  public int getHide() {
    return hide;
  }
  public void setHide(int hide) {
    this.hide = hide;
  }
}
