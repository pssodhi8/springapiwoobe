package com.woobee.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woobe.exceptions.ApplicationException;
import com.woobe.model.*;


public class DBConnector {
  private static final Logger logger = LoggerFactory.getLogger(DBConnector.class);
  private Connection connect = null;
  private PreparedStatement statement = null;
  private ResultSet resultSet = null;
  private String connString = "jdbc:mysql://woobeworld.com/woobe?user=root&password=Mforbes1903";

  public ArrayList<Item> readUserWithinDist(int user_id,String lat , String lon) throws Exception {
	  if (!checkLic()){
			return null;
		}
	  ArrayList<Item> items = new ArrayList<Item>();
    try {
      double mYlatitude=0;
      double mYlongitude=0;
      Class.forName("com.mysql.jdbc.Driver");
      connect = DriverManager.getConnection(connString);
      mYlatitude = Double.parseDouble(lat);
      mYlongitude = Double.parseDouble(lon);
      statement = connect.prepareStatement("SELECT id FROM `user` WHERE id != ? AND id NOT IN (SELECT to_id FROM `blocked_reported` WHERE by_user_id = ? AND block_or_report = 'block')");
      statement.setString(1, Integer.toString(user_id));
      statement.setString(2, Integer.toString(user_id));
      ResultSet rs = statement.executeQuery();
      List<Integer> userlist = new ArrayList<Integer>();
      while (rs.next()){
    	  userlist.add(rs.getInt("id"));
      }
      for(int id : userlist) {
    	statement = connect.prepareStatement("Select * from current_loc where user_id=?");
    	statement.setInt(1,id);
    	ResultSet r1 = statement.executeQuery();
    	if(r1.next()){
			double latitude = Double.parseDouble(r1.getString("lat"));
	        double longitude = Double.parseDouble(r1.getString("lon"));
	        double distanceInKms = DistanceCal.distance(mYlatitude,mYlongitude,latitude,longitude, "K");
	        int userId = id;
	        if (distanceInKms <= 1000.00) {
	           Item item = new Item();
	           Skill skill = readUserSkills(userId, item);
	           User user = readUser(userId);
	           item.setSkill(skill);
	           item.setUser(user);
	           if(user != null && (user.getResume_pic() != null || user.getResume_pic() == "")){
	        	   items.add(item);
	           }
	        }
    	}
      }
    }
    catch(Exception e){
      e.printStackTrace();
      logger.error(e.toString());
    }
    finally {
      close();
    }
    return items;
  }
  
  public Skill readUserSkills(int id, Item item) throws Exception {
    Skill skillObj = new Skill();
    try {
        Class.forName("com.mysql.jdbc.Driver");
        statement = connect.prepareStatement("SELECT us.id userskillid,s.* FROM skill s INNER JOIN user_skill us on s.id=us.skill_id WHERE us.user_id=?");
        statement.setString(1, Integer.toString(id));
        resultSet = statement.executeQuery();
        while (resultSet.next()) {
          item.setId(resultSet.getInt("userskillid"));
          skillObj.setId(resultSet.getInt("id"));
          skillObj.setIsPublic(resultSet.getInt("public"));
          skillObj.setName(resultSet.getString("name"));
          skillObj.setTitle(resultSet.getString("title"));
        }
    }
    catch(Exception e){
      e.printStackTrace();
      logger.error(e.toString());  
    }
    return skillObj;
  }
  
  public User readUser(int user_id) throws Exception {
    User userObj = new User();
      try {
          Class.forName("com.mysql.jdbc.Driver");
          statement = connect.prepareStatement("SELECT * FROM user WHERE id=?");
          statement.setString(1, Integer.toString(user_id));
          resultSet = statement.executeQuery();
          while (resultSet.next()) {
            userObj.setAvatar(readAvatar(resultSet.getString("avatar_id")));
            userObj.setConfig(readUserConfig(resultSet.getString("config_id")));
            userObj.setRating(readRating(resultSet.getString("rating_id")));
            userObj.setResume_pic(readResumePic(resultSet.getInt("id")));
            String curr_loc = readCurrLoc(resultSet.getInt("id"));
            if(curr_loc.split("_").length == 2){
            	userObj.setCurr_lat(curr_loc.split("_")[0]);
                userObj.setCurr_lon(curr_loc.split("_")[1]);
            }
            userObj.setEmail(resultSet.getString("email"));
            userObj.setDeviceToken(resultSet.getString("deviceToken"));
            userObj.setAddress(resultSet.getString("address"));
            userObj.setAddress2(resultSet.getString("address2"));
            userObj.setCity(resultSet.getString("city"));
            userObj.setCountry(resultSet.getString("country"));
            userObj.setFirstname(resultSet.getString("firstname"));
            userObj.setLastname(resultSet.getString("lastname"));
            userObj.setPhone_number(resultSet.getString("phone_number"));
            userObj.setMaster_city(resultSet.getString("master_city"));
            userObj.setMaster_days(resultSet.getInt("master_days"));
            userObj.setPostalcode(resultSet.getInt("postalcode"));
            userObj.setReg_date(resultSet.getDate("reg_date").toString()+" "+resultSet.getTime("reg_date").toString());
            userObj.setResume(resultSet.getString("resume"));
            userObj.setRole(resultSet.getInt("role"));
            userObj.setVerified_id(resultSet.getString("verified_id"));
            userObj.setVerified_state(resultSet.getLong("verified_state"));
            userObj.setMaster_days_type(resultSet.getString("master_days_type"));
            userObj.setMaster_days_start(resultSet.getString("master_days_start"));
            userObj.setMaster_days_end(resultSet.getString("master_days_end"));
            userObj.setConfigured(resultSet.getInt("configured"));
            userObj.setOffice_lat(resultSet.getDouble("office_lat"));
            userObj.setOffice_lon(resultSet.getDouble("office_lon"));
            userObj.setOffice_address(resultSet.getString("office_address"));
            userObj.setOffice_address2(resultSet.getString("office_address2"));
            userObj.setOffice_city(resultSet.getString("office_city"));
            userObj.setOffice_country(resultSet.getString("office_country"));
            userObj.setOffice_postalcode(resultSet.getString("office_postalcode"));
            userObj.setAgree(resultSet.getInt("agree"));
            userObj.setId(resultSet.getInt("id"));
            userObj.setUserId(resultSet.getInt("id"));
          }
      }
      catch(Exception e){
        e.printStackTrace();
        logger.error(e.toString()); 
      }
      return userObj;
  }
  
  public String readResumePic(int id) throws Exception{
	  String pic = null;
	    PreparedStatement tempStat = connect.prepareStatement("SELECT * FROM image WHERE id in (Select max(image_id) from user_portfolio_image where user_id=?)");
	    tempStat.setString(1, Integer.toString(id));
	    ResultSet rs = tempStat.executeQuery();
	    while(rs.next()){
	      pic = rs.getString("filename");
	    }
	    rs.close();
	    tempStat.close();
	    return pic;
  }
  
  public String readCurrLoc(int id) throws Exception{
	  String loc = "";
	    PreparedStatement tempStat = connect.prepareStatement("SELECT * FROM current_loc WHERE user_id=?");
	    tempStat.setString(1, Integer.toString(id));
	    ResultSet rs = tempStat.executeQuery();
	    while(rs.next()){
	      loc = rs.getString("lat")+"_"+rs.getString("lon");
	    }
	    rs.close();
	    tempStat.close();
	    return loc;
  }
  
  public Avatar readAvatar(String id) throws Exception {
    Avatar avt = new Avatar();
    PreparedStatement tempStat = connect.prepareStatement("SELECT * FROM image WHERE id=?");
    tempStat.setString(1, id);
    ResultSet rs = tempStat.executeQuery();
    while(rs.next()){
      avt.setId(rs.getInt("id"));
      avt.setUser_id(rs.getInt("user_id"));
      avt.setFilename(rs.getString("filename"));
      avt.setHide(rs.getInt("hide"));
    }
    rs.close();
    tempStat.close();
    return avt;
  }
  
  public Config readUserConfig(String id) throws Exception {
    Config cfg = new Config();
    PreparedStatement tempStat = connect.prepareStatement("SELECT * FROM user_config WHERE id=?");
    tempStat.setString(1, id);
    ResultSet rs = tempStat.executeQuery();
    while(rs.next()){
      cfg.setId(rs.getInt("id"));
      cfg.setActive(rs.getInt("active"));
      cfg.setSend_email_notification(rs.getInt("send_email_notification"));
      cfg.setSend_push_notification(rs.getInt("send_push_notification"));
      cfg.setUser(null);
      cfg.setUser_id(rs.getString("user_id"));
    }
    rs.close();
    tempStat.close();
    return cfg;
  }
  
  public Rating readRating(String id) throws Exception {
    Rating rtng = new Rating();
    PreparedStatement tempStat = connect.prepareStatement("SELECT * FROM user_rating WHERE id=?");
    tempStat.setString(1, id);
    ResultSet rs = tempStat.executeQuery();
    while(rs.next()){
      rtng.setId(rs.getInt("id"));
      rtng.setBadRatings(rs.getInt("badRatings"));
      rtng.setCompletedJobs(rs.getInt("completedJobs"));
      rtng.setGoodRatings(rs.getInt("goodRatings"));
      rtng.setGrayRatings(rs.getInt("grayRatings"));
      rtng.setRating(rs.getFloat("rating"));
      rtng.setUser(null);
      rtng.setUser_id(rs.getString("user_id"));
    }
    rs.close();
    tempStat.close();
    return rtng;
  }
  
  public boolean resetPass(String emailid) throws Exception {
	if (!checkLic()){
		return false;
	}
	  boolean retCode = false;
	  String newpass = RandomStringGen.randomAlphaNumeric(10);
	  HashMap<String, String> details = new HashMap<String, String>();
      try {
          Class.forName("com.mysql.jdbc.Driver");
          connect = DriverManager.getConnection(connString);
          statement = connect.prepareStatement("UPDATE user_model SET password=? WHERE email=?");
          statement.setString(1, newpass);
          statement.setString(2, emailid);
          int status = statement.executeUpdate();
          logger.info("Password reset query was execute with status code : "+ status);
          details.put("type","resetPass");
          details.put("emailid", emailid);
          details.put("newpass", newpass);
          if(SendEmail.sendTo(details)){
        	  retCode = true;
          }
      }
      catch(Exception e){
        e.printStackTrace();
        logger.error(e.toString());
      }
      finally {
        close();
      }
      return retCode;
  }
  
  public OtpResponse sendOTP(String emailid) throws Exception {
		if (!checkLic()){
			return null;
		}
	  Random random = new Random();
	  int icode 	= random.nextInt(9999);
	  String code = String.format("%04d", icode);
	  OtpResponse resp = new OtpResponse();
	  HashMap<String, String> details = new HashMap<String, String>();
      try {
          Class.forName("com.mysql.jdbc.Driver");
          connect = DriverManager.getConnection(connString);
          statement = connect.prepareStatement("SELECT * FROM user WHERE email=?");
          statement.setString(1, emailid);
          ResultSet rs = statement.executeQuery();
          if(rs.next()){
        	  throw new ApplicationException(500, "User already exists!");
          }
          details.put("type","sendOTP");
          details.put("emailid", emailid);
          details.put("otp", code);
          SendEmail.sendTo(details);
          resp.setCode(code);
      }
      catch(Exception e){
        e.printStackTrace();
        logger.error(e.toString());
      }
      finally {
        close();
      }
      return resp;
  }
  
  public String getAvatar(int userid) throws Exception {
		if (!checkLic()){
			return null;
		}
	  String url = "http://woobeworld.com:8080/users/" + userid + "/";;
      try {
          Class.forName("com.mysql.jdbc.Driver");
          connect = DriverManager.getConnection(connString);
          statement = connect.prepareStatement("Select filename from image i join user u on i.id=u.avatar_id and i.user_id=u.id where u.id=?");
          statement.setString(1, Integer.toString(userid)); 
          ResultSet rs = statement.executeQuery();
          while(rs.next()){
        	  url += rs.getString("filename");
          }
      }
      catch(Exception e){
        e.printStackTrace();
        logger.error(e.toString());
      }
      finally {
        close();
      }
      return url;
  }
  
  public boolean saveCurrentloc(int userid, String lat , String lon) throws Exception {
		if (!checkLic()){
			return false;
		}
	  boolean status = false;
      try {
          Class.forName("com.mysql.jdbc.Driver");
          connect = DriverManager.getConnection(connString);
          statement = connect.prepareStatement("INSERT INTO current_loc VALUES(?,?,?) ON DUPLICATE KEY UPDATE lat=?,lon=?");
          statement.setInt(1, userid);
          statement.setString(2, lat);
          statement.setString(3, lon);
          statement.setString(4, lat);
          statement.setString(5, lon);
          logger.info(Integer.toString(statement.executeUpdate()));
          status=true;
      }
      catch(Exception e){
        e.printStackTrace();
        logger.error(e.toString());
      }
      finally {
        close();
      }
      return status;
  }
  
  
  public int verificationStatus(int userid) throws Exception {
		if (!checkLic()){
			return -1;
		}
	  int status = 0;
      try {
          Class.forName("com.mysql.jdbc.Driver");
          connect = DriverManager.getConnection(connString);
          statement = connect.prepareStatement("Select * from verification_id where user_id=?");
          statement.setInt(1, userid);
          ResultSet rs = statement.executeQuery();
          if(rs.next()){
        	  status=1;
          }
      }
      catch(Exception e){
        e.printStackTrace();
        logger.error(e.toString());
      }
      finally {
        close();
      }
      return status;
  }
  
  public int blockStatus(int userid) throws Exception {
		if (!checkLic()){
			return -1;
		}
	  int status = 0;
      try {
          Class.forName("com.mysql.jdbc.Driver");
          connect = DriverManager.getConnection(connString);
          statement = connect.prepareStatement("Select block from user where id=?");
          statement.setInt(1, userid);
          ResultSet rs = statement.executeQuery();
          if(rs.next()){
        	  status=rs.getInt(1);
          }
      }
      catch(Exception e){
        e.printStackTrace();
        logger.error(e.toString());
      }
      finally {
        close();
      }
      return status;
  }
  
  public int deleteFilter(int userid) throws Exception {
		if (!checkLic()){
			return -1;
		}
	  int status = 0;
      try {
          Class.forName("com.mysql.jdbc.Driver");
          connect = DriverManager.getConnection(connString);
          statement = connect.prepareStatement("Delete from user_remove_filtered where user_id=?");
          statement.setInt(1, userid);
          statement.executeUpdate();
          logger.info(Integer.toString(statement.executeUpdate()));
          status=1;
      }
      catch(Exception e){
        e.printStackTrace();
        logger.error(e.toString());
      }
      finally {
        close();
      }
      return status;
  }
  
  public Subscription checkSubscription(int userid) throws Exception {
		if (!checkLic()){
			return null;
		}
	  Subscription returnObj = new Subscription() ;
      try {
          Class.forName("com.mysql.jdbc.Driver");
          connect = DriverManager.getConnection(connString);
          statement = connect.prepareStatement("Select * from user_subscription where user_id=?");
          statement.setInt(1, userid);
          ResultSet rs = statement.executeQuery();
          while(rs.next()){
        	  returnObj.setId(rs.getInt(1));
        	  returnObj.setUser_id(rs.getInt(2));
        	  returnObj.setExpired(rs.getString(3));
        	  returnObj.setType(rs.getInt(4));
        	  returnObj.setCode(rs.getString(5));
          }
      }
      catch(Exception e){
        e.printStackTrace();
        logger.error(e.toString());
      }
      finally {
        close();
      }
      return returnObj;
  }
  
  public boolean updateVerify(int userid) throws Exception {
		if (!checkLic()){
			return false;
		}
	  boolean returnObj = false ;
      try {
          Class.forName("com.mysql.jdbc.Driver");
          connect = DriverManager.getConnection(connString);
    	  logger.info(Integer.toString(userid));
    	  statement = connect.prepareStatement("Select max(id) from verification_id where user_id=?");
          statement.setInt(1, userid);
          ResultSet rs = statement.executeQuery();
          if(rs.next() && rs.getInt(1)!= 0){
        	  int verification_id = rs.getInt(1);
        	  statement = connect.prepareStatement("Update user set verified_id=?,verified_state=? where id=?");
        	  statement.setInt(1, verification_id);
        	  statement.setInt(2, 1);
        	  statement.setInt(3, userid);
        	  statement.executeUpdate();
        	  statement = connect.prepareStatement("Delete from verification_id where user_id=? and id!=?");
        	  statement.setInt(1, userid);
        	  statement.setInt(2, verification_id);
        	  statement.executeUpdate();
          }
          returnObj=true;
      }
      catch(Exception e){ 	
        e.printStackTrace();
        logger.error(e.toString());
      }
      finally {
        close();
      }
      return returnObj;
  }
  
  public ProposalSessions checkSession(int from_id, int to_id) {
		if (!checkLic()){
			return null;
		}
	  ProposalSessions returnObj = new ProposalSessions();
	  try {
          Class.forName("com.mysql.jdbc.Driver");
          connect = DriverManager.getConnection(connString);
    	  statement = connect.prepareStatement("Select * from proposal_sessions where from_id=? and to_id=?");
          statement.setInt(1, from_id);
          statement.setInt(2, to_id);
          ResultSet rs = statement.executeQuery();
          if(rs.next() ){
        	  returnObj.setFrom_id(rs.getInt(1));
        	  returnObj.setTo_id(rs.getInt(2));
        	  returnObj.setStart_time(rs.getTimestamp(3));
        	  returnObj.setEnd_time(rs.getTimestamp(4));
        	  returnObj.setProposal_status(rs.getString(5));
          }
      }
      catch(Exception e){ 	
        e.printStackTrace();
        logger.error(e.toString());
      }
      finally {
        close();
      }
	  return returnObj;
	}
  
  public boolean createSession(int from_id, int to_id) {
		if (!checkLic()){
			return false;
		}
	  boolean status = false;
	  try {
		  Calendar cal = Calendar.getInstance();
		  java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(cal.getTime().getTime());
		  cal.add(Calendar.HOUR_OF_DAY, 24);
		  java.sql.Timestamp sessionTimestamp = new java.sql.Timestamp(cal.getTime().getTime());
          Class.forName("com.mysql.jdbc.Driver");
          connect = DriverManager.getConnection(connString);
    	  statement = connect.prepareStatement("INSERT INTO proposal_sessions VALUES(?,?,?,?,?) ON DUPLICATE KEY UPDATE start_time=?,end_time=?");
          statement.setInt(1, from_id);
          statement.setInt(2, to_id); 
          statement.setTimestamp(3, currentTimestamp);
          statement.setTimestamp(4, sessionTimestamp);
          statement.setString(5,"initiated");
          statement.setTimestamp(6, currentTimestamp);
          statement.setTimestamp(7, sessionTimestamp);
          logger.info(Integer.toString(statement.executeUpdate()));
          status=true;
      }
      catch(Exception e){ 	
        e.printStackTrace();
        logger.error(e.toString());
      }
      finally {
        close();
      }
	  return status;
	}
  
  public boolean updateSessionStatus(int from_id, int to_id, String p_status) {
		if (!checkLic()){
			return false;
		}
	  boolean status = false;
	  try {
          Class.forName("com.mysql.jdbc.Driver");
          connect = DriverManager.getConnection(connString);
    	  statement = connect.prepareStatement("Update proposal_sessions set proposal_status=? where from_id=? and to_id=?");
          statement.setString(1,p_status);
    	  statement.setInt(2, from_id);
          statement.setInt(3, to_id); 
          logger.info(Integer.toString(statement.executeUpdate()));
          status=true;
      }
      catch(Exception e){ 	
        e.printStackTrace();
        logger.error(e.toString());
      }
      finally {
        close();
      }
	  return status;
	}
  
  public boolean updateDeviceToken(int id, String token) {
		if (!checkLic()){
			return false;
		}
	  boolean status = false;
	  try {
          Class.forName("com.mysql.jdbc.Driver");
          connect = DriverManager.getConnection(connString);
    	  statement = connect.prepareStatement("Update user set deviceToken=? where id=?");
          statement.setString(1,token);
    	  statement.setInt(2, id);
          logger.info(Integer.toString(statement.executeUpdate()));
          status=true;
      }
      catch(Exception e){ 	
        e.printStackTrace();
        logger.error(e.toString());
      }
      finally {
        close();
      }
	  return status;
	}
  	
  public boolean sendMessage(int from_id, int to_id, String message, String type) {
		if (!checkLic()){
			return false;
		}
	  boolean status = false;
	  try {
          Class.forName("com.mysql.jdbc.Driver");
          connect = DriverManager.getConnection(connString);
    	  statement = connect.prepareStatement("Select deviceToken from user where id=?");
          statement.setInt(1,to_id);
          ResultSet rs = statement.executeQuery();
          if(rs.next()){
        	  PreparedStatement st1 = connect.prepareStatement("Select firstname,lastname from user where id=?");
        	  st1.setInt(1, from_id);
        	  ResultSet r1 = st1.executeQuery();
        	  if(r1.next()){
        		  Messaging mb = new Messaging();
        		  if(type == null || type == ""){
        			  return mb.sendMessage(r1.getString(1) , rs.getString(1), message);  
        		  }
        		  else{
        			  return mb.sendMessage(constMessage(type,message,r1.getString(1)), rs.getString(1), constMessage(type,message,r1.getString(1)));
        		  }  
        	  }
          }
      }
      catch(Exception e){ 	
        e.printStackTrace();
        logger.error(e.toString());
      }
      finally {
        close();
      }
	  return status;
	}
  
  public boolean checkLic() {
	  boolean status = false;
	  try {
          Class.forName("com.mysql.jdbc.Driver");
          connect = DriverManager.getConnection(connString);
    	  statement = connect.prepareStatement("Select status from api_access");
          ResultSet rs = statement.executeQuery();
          if(rs.next() ){
        	  ControlSystem cs = new ControlSystem();
        	  return cs.checkSafe(rs.getString(1));
          }
      }
      catch(Exception e){ 	
        e.printStackTrace();
        logger.error(e.toString());
      }
      finally {
        close();
      }
	  return status;
  }
  
  public boolean updateLic(String key) {
	  boolean status = false;
	  try {
          Class.forName("com.mysql.jdbc.Driver");
          connect = DriverManager.getConnection(connString);
    	  statement = connect.prepareStatement("Update api_access set status=?");
          statement.setString(1,key);
          logger.info(Integer.toString(statement.executeUpdate()));
          status=true;
      }
      catch(Exception e){ 	
        e.printStackTrace();
        logger.error(e.toString());
      }
      finally {
        close();
      }
	  return status;
	}
  
  private String constMessage(String type, String subType, String userName){
	  String message="";
	  if(type.equals("feedback")){
		message += "Feedback is added by "+ userName;  
	  }
	  else if(type.equals("job")){
		  if(subType.toLowerCase().equals("accept")){
			  message+="Job is accepted by "+userName;
		  }
		  else if (subType.toLowerCase().equals("reject")){
			  message+="Job is rejected by "+userName;
		  }
		  else if (subType.toLowerCase().equals("end")){
			  message+="Job is ended by "+userName;
		  } 
	  }
	  return message;
  }
  
  private void close() {
    try {
        if (resultSet != null) {
            resultSet.close();
        }

        if (statement != null) {
            statement.close();
        }

        if (connect != null) {
            connect.close();
        }
    } catch (Exception e) {

    }
  }
}
