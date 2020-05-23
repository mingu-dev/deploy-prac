package com.prac.web;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	private List<User> users = new ArrayList<User>();

	@PostMapping("/create")
	public String create(User user) {
		
		log.info("user : " + user);
		users.add(user);
		
		return "redirect:/list";
	}
	
	@GetMapping("/list")
	public String list(Model model) {
		
		model.addAttribute("users", users);
		
		return "list";
	}
	
}
