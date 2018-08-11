package com.woobee.utils;

public class RandomStringGen {

	private static final String KEYWORDS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_#";
	
	public static String randomAlphaNumeric(int count) {
		StringBuilder sb = new StringBuilder();
		while (count-- != 0) {
		int idex = (int)(Math.random()*KEYWORDS.length());
		sb.append(KEYWORDS.charAt(idex));
		}
		return sb.toString();
	}
}
