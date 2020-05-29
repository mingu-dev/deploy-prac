package com.prac.web;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.prac.domain.User;
import com.prac.domain.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/loginForm")
	public String login() {
		
		return "/user/login";
	}
	
	@GetMapping("/join")
	public String join() {
		
		return "/user/form";
	}

	@PostMapping("/create")
	public String create(User user) {
		
		log.info("user : " + user);
		userRepository.save(user);
		
		return "redirect:/user/list";
	}
	
	@GetMapping("/list")
	public String list(Model model) {
		
		List<User> users = userRepository.findAll();
		for (User user : users) log.info(user.toString());
		
		model.addAttribute("users", userRepository.findAll());
		
		return "/user/list";
	}
	
	@GetMapping("/{id}")
	public String modify(@PathVariable Long id, Model model) throws Exception {
		
		Optional<User> optional = userRepository.findById(id);
		
		if (optional.isPresent()) {
			model.addAttribute("user", optional.get());
			return "/user/update";
		}
		
		throw new Exception();
	}
	
	@PutMapping("/update/{id}")
	public String update(@PathVariable Long id, User newUser) {
		
		User user = userRepository.findById(id).get();
		user.update(newUser);
		userRepository.save(user);
		
		return "redirect:/user/list";
	}
}
