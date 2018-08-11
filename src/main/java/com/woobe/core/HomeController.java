package com.woobe.core;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woobe.model.*;
import com.woobee.utils.*;

import org.springframework.ui.Model;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome to Woobe Core Apis! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value = "api/data/load/userskills", method = RequestMethod.GET)
	public @ResponseBody UserSkillsResponse getUserSkills(@RequestParam("user_id") int userId,@RequestParam("lat") String lat,@RequestParam("lon") String lon) throws Exception {
	  DBConnector db = new DBConnector();
	  UserSkillsResponse uskr = new UserSkillsResponse();
	  uskr.setVersion(0);
	  uskr.setItems(db.readUserWithinDist(userId,lat,lon));
	  return uskr;
	}
	
	@RequestMapping(value = "api/user/resetPass", method = RequestMethod.GET)
	public @ResponseBody boolean resetPass(@RequestParam("emailid") String emailid) throws Exception {
	  DBConnector db = new DBConnector();
	  return db.resetPass(emailid);
	}
	
	@RequestMapping(value = "api/user/sendOTP", method = RequestMethod.GET)
	public @ResponseBody OtpResponse sendOTP(@RequestParam("emailid") String emailid) throws Exception {
	  DBConnector db = new DBConnector();
	  return db.sendOTP(emailid);
	}
	
	@RequestMapping(value = "api/user/getAvatar", method = RequestMethod.GET)
	public @ResponseBody String getAvatar(@RequestParam("user_id") int userId) throws Exception {
	  DBConnector db = new DBConnector();
	  return db.getAvatar(userId);
	}
	
	@RequestMapping(value = "api/user/saveCurrentloc", method = RequestMethod.GET)
	public @ResponseBody boolean saveCurrentloc(@RequestParam("user_id") int userId,@RequestParam("lat") String lat,@RequestParam("lon") String lon) throws Exception {
	  DBConnector db = new DBConnector();
	  return db.saveCurrentloc(userId,lat,lon);
	}
	
	@RequestMapping(value = "api/user/checkVerification", method = RequestMethod.GET)
	public @ResponseBody int checkVerification(@RequestParam("user_id") int userId) throws Exception {
	  DBConnector db = new DBConnector();
	  return db.verificationStatus(userId);
	}
	
	@RequestMapping(value = "api/user/checkStatus", method = RequestMethod.GET)
	public @ResponseBody int checkStatus(@RequestParam("user_id") int userId) throws Exception {
	  DBConnector db = new DBConnector();
	  return db.blockStatus(userId);
	}
	
	@RequestMapping(value = "api/user/deleteFilter", method = RequestMethod.GET)
	public @ResponseBody int deleteFilter(@RequestParam("user_id") int userId) throws Exception {
	  DBConnector db = new DBConnector();
	  return db.deleteFilter(userId);
	}
	
	@RequestMapping(value = "api/user/checkSubscription", method = RequestMethod.GET)
	public @ResponseBody Subscription checkSubscription(@RequestParam("user_id") int userId) throws Exception {
	  DBConnector db = new DBConnector();
	  return db.checkSubscription(userId);
	}
	
	@RequestMapping(value = "api/user/updateVerify", method = RequestMethod.GET)
	public @ResponseBody boolean updateVerify(@RequestParam("user_id") int userId) throws Exception {
	  DBConnector db = new DBConnector();
	  return db.updateVerify(userId);
	}
	
	@RequestMapping(value = "api/user/checkSession", method = RequestMethod.GET)
	public @ResponseBody ProposalSessions checkSession(@RequestParam("from_id") int from_id, @RequestParam("to_id") int to_id) throws Exception {
	  DBConnector db = new DBConnector();
	  return db.checkSession(from_id,to_id);
	}
	
	@RequestMapping(value = "api/user/createSession", method = RequestMethod.GET)
	public @ResponseBody boolean createSession(@RequestParam("from_id") int from_id, @RequestParam("to_id") int to_id) throws Exception {
	  DBConnector db = new DBConnector();
	  return db.createSession(from_id,to_id);
	}
	
	@RequestMapping(value = "api/user/updateSessionStatus", method = RequestMethod.GET)
	public @ResponseBody boolean updateSessionStatus(@RequestParam("from_id") int from_id, @RequestParam("to_id") int to_id , @RequestParam("status") String p_status) throws Exception {
	  DBConnector db = new DBConnector();
	  return db.updateSessionStatus(from_id, to_id, p_status);
	}
	
	@RequestMapping(value = "api/user/updateDeviceToken", method = RequestMethod.GET)
	public @ResponseBody boolean updateDeviceToken(@RequestParam("id") int id, @RequestParam("token") String token) throws Exception {
	  DBConnector db = new DBConnector();
	  return db.updateDeviceToken(id, token);
	}
	
	@RequestMapping(value = "api/user/sendMessage", method = RequestMethod.GET)
	public @ResponseBody boolean sendMessage(@RequestParam("from_id") int from_id, @RequestParam("to_id") int to_id, @RequestParam("message") String message, @RequestParam(value="type",required = false) String type) throws Exception {
	  DBConnector db = new DBConnector();
	  return db.sendMessage(from_id, to_id, message,type);
	}
	
	@RequestMapping(value = "api/activate", method = RequestMethod.GET)
	public @ResponseBody String updateLic(@RequestParam("license") String secretWord) throws Exception {
		DBConnector db = new DBConnector();
	  if(db.updateLic(secretWord)){
		  return "License Applied successfully !";
	  }
	  return "License activation failed!";
	}
}
