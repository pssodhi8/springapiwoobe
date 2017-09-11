package com.woobe.core;

import java.text.DateFormat;
import java.util.ArrayList;
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
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value = "api/data/load/userskills", method = RequestMethod.GET)
  public @ResponseBody UserSkillsResponse getUserSkills(@RequestParam("user_id") int userId) throws Exception {
	  DBConnector db = new DBConnector();
	  
	  Item item = new Item();
	  Skill skill = db.readUserSkills(userId, item);
	  User user = db.readUser(userId);
	  
	  ArrayList<Item> items = new ArrayList<Item>();
	  item.setSkill(skill);
	  item.setUser(user);
	  items.add(item);
	  
	  UserSkillsResponse uskr = new UserSkillsResponse();
    uskr.setVersion(0);
    uskr.setItems(items);
    
	  return uskr;
	}
	
}
