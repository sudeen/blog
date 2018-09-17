package com.sudin.blog.controllers;

import com.sudin.blog.configs.CustomUserDetails;
import com.sudin.blog.entities.Post;
import com.sudin.blog.service.PostService;
import com.sudin.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;


/*To return the view of HTML use @Controller annotation instead of @RestController*/
/*@RestController does not allow to return the view as @Controller does
* While using @RestController ModelAndView is used to return the view as usual*/
@RestController
public class BlogController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        /*This view name is the html page name that is in the resources folder*/
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping(value = "/posts")
    public List<Post> posts() {
        return postService.getAllPosts();
    }

    @PostMapping(value = "/post")
    public String publishPosts(@RequestBody Post post) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (post.getDateCreated() == null)
            post.setDateCreated(new Date());
        post.setCreator(userService.getUser(userDetails.getUsername()));
        postService.insert(post);

        return "published";
    }

    @GetMapping(value = "/posts/{username}")
    public List<Post> postsByUsername(@PathVariable String username){
        return postService.findByUser(userService.getUser(username));
    }

}
