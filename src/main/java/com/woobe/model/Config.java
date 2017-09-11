package com.woobe.model;

import java.io.Serializable;

public class Config implements Serializable{

  private static final long serialVersionUID = -3138220029221856944L;
  int id;
  int active;
  int send_email_notification;
  int send_push_notification;
  String user_id;
  String User;
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public int getActive() {
    return active;
  }
  public void setActive(int active) {
    this.active = active;
  }
  public int getSend_email_notification() {
    return send_email_notification;
  }
  public void setSend_email_notification(int send_email_notification) {
    this.send_email_notification = send_email_notification;
  }
  public int getSend_push_notification() {
    return send_push_notification;
  }
  public void setSend_push_notification(int send_push_notification) {
    this.send_push_notification = send_push_notification;
  }
  public String getUser_id() {
    return user_id;
  }
  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }
  public String getUser() {
    return User;
  }
  public void setUser(String user) {
    User = user;
  }
}
