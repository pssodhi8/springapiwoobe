package com.woobe.model;

import java.io.Serializable;

public class Subscription implements Serializable{
	private static final long serialVersionUID = 6255323194944482783L;
	int id;
	int user_id;
	String expired;
	int type;
	String code;
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getId() {
		return id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
