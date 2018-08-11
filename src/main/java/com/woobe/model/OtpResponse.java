package com.woobe.model;

import java.io.Serializable;

public class OtpResponse implements Serializable {
	private static final long serialVersionUID = -8307662711735604799L;
	String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
