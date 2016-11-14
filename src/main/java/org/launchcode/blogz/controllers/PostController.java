package org.launchcode.blogz.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.launchcode.blogz.models.Post;
import org.launchcode.blogz.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PostController extends AbstractController {

	@RequestMapping(value = "/blog/newpost", method = RequestMethod.GET)
	public String newPostForm() {
		return "newpost";
	}
	
	@RequestMapping(value = "/blog/newpost", method = RequestMethod.POST)
	public String newPost(HttpServletRequest request, Model model) {
		
		// TODO - implement newPost
		
		//get requests parameters
		String title = request.getParameter("title");
		String body = request.getParameter("body");
		
		
		//validate parameters
		if(title == ""){
			model.addAttribute("error", "Please include a title");
			return "/blog/newpost";
		}
		
		if(body == ""){
			model.addAttribute("error", "Empty post, please add text");
			return "/blog/newpost";
		}
		
		//if valid, create new posts (need to use getUserBySession for author)
		Post post = new Post(title, body, getUserFromSession(request.getSession()));
		postDao.save(post); //saves post to database
		
		
		//if not valid, send back to form with error message
		
		return "redirect:index"; // TODO - this redirect should go to the new post's page  		
	}
	
	//handles requests like "/blob/bob/5"
	@RequestMapping(value = "/blog/{username}/{uid}", method = RequestMethod.GET)
	public String singlePost(@PathVariable String username, @PathVariable int uid, Model model) {
		
		// TODO - implement singlePost
		//find the user
		User user = userDao.findByUsername(username);
		
				
		//get given post
		Post post = postDao.findByUid(uid);
		
		//validate the user:  check if user == null, post == null, user is same as post author
		if(user == null || post == null || !user.getUsername().equals(username)){
			return "notfound";
		}
		
		//pass the post into the template
		model.addAttribute("post", post);
		
		return "post";
	}
	
	@RequestMapping(value = "/blog/{username}", method = RequestMethod.GET)
	public String userPosts(@PathVariable String username, Model model) {
		
		// TODO - implement userPosts
		
		//get all the user's posts
		//findbyUserName first, then put that into findByAuthor
		User user = userDao.findByUsername(username);
		if(user == null){
			return "notfound";
		}
		
		List<Post> posts = postDao.findByAuthor(user);
		
		//pass the post into the template
		//use model.addAttribute("name", listOfPosts()) then iterate thru them in the template
		model.addAttribute("posts", posts);
		
		return "blog";
	}
	
}
