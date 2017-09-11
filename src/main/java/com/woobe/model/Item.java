package com.woobe.model;

import java.io.Serializable;

public class Item implements Serializable{

  private static final long serialVersionUID = 7806237590286253298L;
  int id;
  User user;
  Skill skill;
  
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public Skill getSkill() {
    return skill;
  }
  public void setSkill(Skill skill) {
    this.skill = skill;
  }
  public User getUser() {
    return user;
  }
  public void setUser(User user) {
    this.user = user;
  }

}
