package com.woobe.model;

import java.io.Serializable;

public class User implements Serializable{
  private static final long serialVersionUID = -7822210781875857792L;

  Avatar avatar;
  com.woobe.model.Config config;
  Rating rating;
  String social;
  
  String email;
  String deviceToken;
  String address;
  String address2;
  String city;
  String country;
  String firstname;
  String lastname;
  String phone_number;
  String master_city;
  int master_days;
  long postalcode;
  String reg_date;
  String resume;
  int role;
  String verified_id;
  long verified_state;
  String master_days_type;
  String master_days_start;
  String master_days_end;
  int configured;
  double office_lat;
  double office_lon;
  String office_address;
  String office_address2;
  String office_city;
  String office_country;
  String office_postalcode;
  int agree;
  int id;
  int userId;
  
  public Avatar getAvatar() {
    return avatar;
  }
  public void setAvatar(Avatar avatar) {
    this.avatar = avatar;
  }
  public com.woobe.model.Config getConfig() {
    return config;
  }
  public void setConfig(com.woobe.model.Config config) {
    this.config = config;
  }
  public Rating getRating() {
    return rating;
  }
  public void setRating(Rating rating) {
    this.rating = rating;
  }
  public String getSocial() {
    return social;
  }
  public void setSocial(String social) {
    this.social = social;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getDeviceToken() {
    return deviceToken;
  }
  public void setDeviceToken(String deviceToken) {
    this.deviceToken = deviceToken;
  }
  public String getAddress() {
    return address;
  }
  public void setAddress(String address) {
    this.address = address;
  }
  public String getAddress2() {
    return address2;
  }
  public void setAddress2(String address2) {
    this.address2 = address2;
  }
  public String getCity() {
    return city;
  }
  public void setCity(String city) {
    this.city = city;
  }
  public String getCountry() {
    return country;
  }
  public void setCountry(String country) {
    this.country = country;
  }
  public String getFirstname() {
    return firstname;
  }
  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }
  public String getLastname() {
    return lastname;
  }
  public void setLastname(String lastname) {
    this.lastname = lastname;
  }
  public String getPhone_number() {
    return phone_number;
  }
  public void setPhone_number(String phone_number) {
    this.phone_number = phone_number;
  }
  public String getMaster_city() {
    return master_city;
  }
  public void setMaster_city(String master_city) {
    this.master_city = master_city;
  }
  public int getMaster_days() {
    return master_days;
  }
  public void setMaster_days(int master_days) {
    this.master_days = master_days;
  }
  public long getPostalcode() {
    return postalcode;
  }
  public void setPostalcode(long postalcode) {
    this.postalcode = postalcode;
  }
  public String getReg_date() {
    return reg_date;
  }
  public void setReg_date(String reg_date) {
    this.reg_date = reg_date;
  }
  public String getResume() {
    return resume;
  }
  public void setResume(String resume) {
    this.resume = resume;
  }
  public int getRole() {
    return role;
  }
  public void setRole(int role) {
    this.role = role;
  }

  public long getVerified_state() {
    return verified_state;
  }
  public void setVerified_state(long verified_state) {
    this.verified_state = verified_state;
  }


  public String getVerified_id() {
    return verified_id;
  }
  public void setVerified_id(String verified_id) {
    this.verified_id = verified_id;
  }
  public String getMaster_days_type() {
    return master_days_type;
  }
  public void setMaster_days_type(String master_days_type) {
    this.master_days_type = master_days_type;
  }
  public String getMaster_days_start() {
    return master_days_start;
  }
  public void setMaster_days_start(String master_days_start) {
    this.master_days_start = master_days_start;
  }
  public String getMaster_days_end() {
    return master_days_end;
  }
  public void setMaster_days_end(String master_days_end) {
    this.master_days_end = master_days_end;
  }
  public int getConfigured() {
    return configured;
  }
  public void setConfigured(int configured) {
    this.configured = configured;
  }
  public double getOffice_lat() {
    return office_lat;
  }
  public void setOffice_lat(double office_lat) {
    this.office_lat = office_lat;
  }
  public double getOffice_lon() {
    return office_lon;
  }
  public void setOffice_lon(double office_lon) {
    this.office_lon = office_lon;
  }
  public String getOffice_address() {
    return office_address;
  }
  public void setOffice_address(String office_address) {
    this.office_address = office_address;
  }
  public String getOffice_address2() {
    return office_address2;
  }
  public void setOffice_address2(String office_address2) {
    this.office_address2 = office_address2;
  }
  public String getOffice_city() {
    return office_city;
  }
  public void setOffice_city(String office_city) {
    this.office_city = office_city;
  }
  public String getOffice_country() {
    return office_country;
  }
  public void setOffice_country(String office_country) {
    this.office_country = office_country;
  }
  public String getOffice_postalcode() {
    return office_postalcode;
  }
  public void setOffice_postalcode(String office_postalcode) {
    this.office_postalcode = office_postalcode;
  }
  public int getAgree() {
    return agree;
  }
  public void setAgree(int agree) {
    this.agree = agree;
  }
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public int getUserId() {
    return userId;
  }
  public void setUserId(int userId) {
    this.userId = userId;
  }
}
