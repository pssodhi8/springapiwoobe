package com.woobee.utils;

import java.sql.*;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.woobe.model.*;


public class DBConnector {
  private static final Logger logger = LoggerFactory.getLogger(DBConnector.class);
  private Connection connect = null;
  private PreparedStatement statement = null;
  private ResultSet resultSet = null;
  private String connString = "jdbc:mysql://<host>/<db>?user=<uname>&password=<pwd>";

  public ArrayList<Item> readUserWithinDist(int user_id) throws Exception {
    ArrayList<Item> items = new ArrayList<Item>();
    try {
      double mYlatitude=0;
      double mYlongitude=0;
      Class.forName("com.mysql.jdbc.Driver");
      connect = DriverManager.getConnection(connString);
      PreparedStatement statement = connect.prepareStatement("SELECT * FROM user WHERE id =?");
      statement.setString(1, Integer.toString(user_id));
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        mYlatitude = rs.getDouble("office_lat");
        mYlongitude = rs.getDouble("office_lon");
      }
      statement = connect.prepareStatement("SELECT * FROM `user` WHERE id != ? AND id NOT IN (SELECT to_id FROM `blocked_reported` WHERE by_user_id = ? AND block_or_report = 'block')");
      statement.setString(1, Integer.toString(user_id));
      statement.setString(2, Integer.toString(user_id));
      rs = statement.executeQuery();
      while (rs.next()) {
        double latitude = rs.getDouble("office_lat");
        double longitude = rs.getDouble("office_lon");
        double distanceInKms = DistanceCal.distance(mYlatitude,mYlongitude,latitude,longitude, "K");
        int userId = rs.getInt("id");
        if (distanceInKms <= 500.00) {
          
          Item item = new Item();
          Skill skill = readUserSkills(userId, item);
          User user = readUser(userId);
          
          item.setSkill(skill);
          item.setUser(user);
          items.add(item);
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
        connect = DriverManager.getConnection(connString);
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
    finally {
      close();
    }
    return skillObj;
  }
  
  public User readUser(int user_id) throws Exception {
    User userObj = new User();
      try {
          Class.forName("com.mysql.jdbc.Driver");
          connect = DriverManager.getConnection(connString);
          statement = connect.prepareStatement("SELECT * FROM user WHERE id=?");
          statement.setString(1, Integer.toString(user_id));
          resultSet = statement.executeQuery();
          while (resultSet.next()) {
            userObj.setAvatar(readAvatar(resultSet.getString("avatar_id")));
            userObj.setConfig(readUserConfig(resultSet.getString("config_id")));
            userObj.setRating(readRating(resultSet.getString("rating_id")));
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
      finally {
        close();
      }
      return userObj;
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
