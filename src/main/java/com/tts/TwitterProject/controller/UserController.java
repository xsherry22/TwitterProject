package com.tts.TwitterProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tts.TwitterProject.model.Tweet;
import com.tts.TwitterProject.model.User;
import com.tts.TwitterProject.service.TweetService;
import com.tts.TwitterProject.service.UserService;

@Controller
public class UserController {

	    @Autowired
	    private UserService userService;
	    
	    @Autowired
	    private TweetService tweetService;
	    
	    @GetMapping(value = "/users/{username}")
	    public String getUser(@PathVariable(value="username") String username, Model model) {	
	    	System.out.println("Triggered");
	        User user = userService.findByUsername(username);
	        List<Tweet> tweets = tweetService.findAllByUser(user);
	        model.addAttribute("tweetList", tweets);
	        model.addAttribute("user", user);
	        return "user";
	    }
}
