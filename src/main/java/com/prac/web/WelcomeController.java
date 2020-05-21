package com.prac.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

	private static final Logger log = LoggerFactory.getLogger(WelcomeController.class);
	
	@GetMapping("/helloworld")
	public String welcome(String name, int age, Model model) {
		
		log.info("/helloworld");
		log.info("name : " + name + "age : " + age);
		
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		
		return "welcome";
	}
}
