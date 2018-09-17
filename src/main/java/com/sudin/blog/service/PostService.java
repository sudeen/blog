package com.sudin.blog.service;

import com.sudin.blog.entities.Post;
import com.sudin.blog.entities.User;
import com.sudin.blog.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public void insert(Post post) {
        postRepository.save(post);
    }

    public List<Post> findByUser(User user) {
        return postRepository.findByCreatorId(user.getId());
    }

    public boolean deletePost(Long postId){
        Post thePost = postRepository.getOne(postId);
        if(thePost == null)
            return false;
        postRepository.deleteById(postId);
        return true;
    }

    public Post getPost(Long id) {
        return postRepository.getOne(id);
    }

    public Post find(Long postId) {
        return postRepository.getOne(postId);
    }
}
