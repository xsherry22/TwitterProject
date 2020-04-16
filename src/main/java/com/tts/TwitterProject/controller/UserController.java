package com.tts.TwitterProject.controller;

import java.util.HashMap;
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
    
    private void setTweetCounts(List<User> users, Model model) {
        HashMap<String,Integer> tweetCounts = new HashMap<>();
        for (User user : users) {
            List<Tweet> tweets = tweetService.findAllByUser(user);
            tweetCounts.put(user.getUsername(), tweets.size());
        }
        model.addAttribute("tweetCounts", tweetCounts);
    }

    
    @GetMapping(value = "/users/{username}")
    public String getUser(@PathVariable(value="username") String username, Model model) {
    	
    	
    	User loggedInUser = userService.getLoggedInUser();
    	
    	boolean isSelfPage = loggedInUser.getUsername().equals(username);
    	model.addAttribute("isSelfPage", isSelfPage);

    	List<User> following = loggedInUser.getFollowing();
    	boolean isFollowing = false;
    	for (User followedUser : following) {
    	    if (followedUser.getUsername().equals(username)) {
    	        isFollowing = true;
    	    }
    	}
    	model.addAttribute("following", isFollowing);

        User user = userService.findByUsername(username);
        List<Tweet> tweets = tweetService.findAllByUser(user);
        model.addAttribute("tweetList", tweets);
        model.addAttribute("user", user);
        return "user";
    }
    
    private void setFollowingStatus(List<User> users, List<User> usersFollowing, Model model) {
        HashMap<String,Boolean> followingStatus = new HashMap<>();
        String username = userService.getLoggedInUser().getUsername();
        
        for (User user : users) {
            if(usersFollowing.contains(user)) {
                followingStatus.put(user.getUsername(), true);
            }else if (!user.getUsername().equals(username)) {
                followingStatus.put(user.getUsername(), false);
        	}
        }
        model.addAttribute("followingStatus", followingStatus);
    }
    
    @GetMapping("/users")
    public String getUsers(Model model) {
    	
    	List<User> users = userService.findAll();
    	
    	User loggedInUser = userService.getLoggedInUser();
    	List<User> usersFollowing = loggedInUser.getFollowing();
    	setFollowingStatus(users, usersFollowing, model);
    	
    	
    	model.addAttribute("users", users);
    	setTweetCounts(users, model);
    	return "users";
    }
}
