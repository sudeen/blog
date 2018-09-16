package com.sudin.blog.controllers;

import com.sudin.blog.configs.CustomUserDetails;
import com.sudin.blog.entities.Post;
import com.sudin.blog.service.PostService;
import com.sudin.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/*To return the view of HTML use @Controller annotation instead of @RestController*/
@Controller
public class BlogController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/")
    public String index() {
        return "index";
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

        return "post was published";
    }

    @GetMapping(value = "/posts/{username}")
    public List<Post> postsByUsername(@PathVariable String username){
        return postService.findByUser(userService.getUser(username));
    }

}
