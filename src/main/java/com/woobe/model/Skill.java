package com.woobe.model;

import java.io.Serializable;

public class Skill implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -7450140636751259879L;
  
  int id;
  String name;
  String title;
  int isPublic;
  
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public int getIsPublic() {
    return isPublic;
  }
  public void setIsPublic(int isPublic) {
    this.isPublic = isPublic;
  }
}
