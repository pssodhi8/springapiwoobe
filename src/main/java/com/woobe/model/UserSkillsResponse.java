package com.woobe.model;

import java.io.Serializable;
import java.util.ArrayList;

public class UserSkillsResponse implements Serializable {
  private static final long serialVersionUID = -5933083666410923054L;
  
  ArrayList<Item> items;
  int version;
  
  public ArrayList<Item> getItems() {
    return items;
  }
  public void setItems(ArrayList<Item> items) {
    this.items = items;
  }
  
  public int getVersion() {
    return version;
  }
  public void setVersion(int version) {
    this.version = version;
  }
  
}
