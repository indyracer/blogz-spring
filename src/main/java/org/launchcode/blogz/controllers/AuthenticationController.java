package org.launchcode.blogz.controllers;

import javax.servlet.http.HttpServletRequest;

import org.launchcode.blogz.models.User;
import org.launchcode.blogz.models.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthenticationController extends AbstractController {
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signupForm() {
		return "signup";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(HttpServletRequest request, Model model) {
		
		// TODO - implement signup
		
		//get parameters from request
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String verify = request.getParameter("verify");
		
		//validate parameters (username, password, verify)
		if(!User.isValidUsername(username)){
			
			model.addAttribute("username_error", "Invalid Username, please try again");
			return "signup";
		}
		
		if(!User.isValidPassword(password)){
			model.addAttribute("password_error", "Invalid Password, please try again");
			return "signup";
		}
		
		if(!password.equals(verify)){
			model.addAttribute("verify_error", "Passwords do not match, please try again");
			return "signup";
		}
		
		//if they validate, create new user and put them in the session..use abstractControllers setUserInSession() method to do this
				
		User newUser = new User(username, password);
		userDao.save(newUser); //saves user to database
		setUserInSession(request.getSession(), newUser);//figure out what the session object is and how to implement it
		
		
		//if fails, state the error and redirect back to login form
		
		return "redirect:blog/newpost";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm() {
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model) {
		
		// TODO - implement login
		
		//get parameters from request
		
		//get user from the username
		
		//check password
		
		//log them in, if so (i.e. set user in the session method)
		
		return "redirect:blog/newpost";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request){
        request.getSession().invalidate();
		return "redirect:/";
	}
}
