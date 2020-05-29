package com.prac.web;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

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
	public String loginForm() {
		
		return "/user/login";
	}
	
	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session) {
		
		User user = userRepository.findByUserId(userId);
		
		if (user == null) {
			log.info("해당하는 회원이 존재하지 않습니다.");
			return "redirect:/user/list";
		}
		
		if (!user.getPassword().equals(password)) {
			log.info("비밀번호가 틀립니다.");
			return "redirect:/user/list";			
		}
		
		session.setAttribute("user", user);
		log.info("로그인 성공!");
		
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.removeAttribute("user");
		log.info("로그아웃!");
		
		return "redirect:/";
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
