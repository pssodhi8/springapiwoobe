package com.woobee.utils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Messaging {
	public final static String AUTH_KEY_FCM = "AAAA4nJpZys:APA91bGm4eUZIhZGUiM27LHIEIst8u0MMWa13OemfnzMx_A01QVlyES7-fYivJ0rRMqk5K8d1rX0w3o3KytTtHZt3n0NnSJ-6OYx-wkvhzZFkxUlJZXBTAiFDPjcVLUWXc1pQwYjim9l";
	public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
	private static final Logger logger = LoggerFactory.getLogger(Messaging.class);
	
	public boolean sendMessage(String from_id, String to_id, String message) throws IOException {
		  boolean status = false;
		  String authKey = AUTH_KEY_FCM; // You FCM AUTH key
		  String FMCurl = API_URL_FCM; 
		  try{
		   URL url = new URL(FMCurl);
		   HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		   conn.setUseCaches(false);
		   conn.setDoInput(true);
		   conn.setDoOutput(true);

		   conn.setRequestMethod("POST");
		   conn.setRequestProperty("Authorization","key="+authKey);
		   conn.setRequestProperty("Content-Type","application/json");

		   JSONObject json = new JSONObject();
		   json.put("to",to_id.trim());
		   JSONObject info = new JSONObject();
		   info.put("title", from_id); // Notification title
		   info.put("body", message); // Notification body
		   info.put("badge", 1);
		   info.put("sound", "default");
		   json.put("notification", info);

		   OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		   wr.write(json.toString());
		   wr.flush();
		   conn.getInputStream();
		   return true;
		}
		 catch(Exception e){
			 e.printStackTrace();
		     logger.error(e.toString());
		 }
		  return status;
		}
}
