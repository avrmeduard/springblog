package com.avrm.springblog.service;

import com.avrm.springblog.dto.PostDto;
import com.avrm.springblog.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PostService {

    @Autowired
    private AuthService authService;

    public void createPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(post.getTitle());
        post.setContent(postDto.getContent());
        // we need to find the current user fom AuthService other while throw runtime exception
        User username = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("No user login"));
        post.setUsername(username.getUsername());
        post.setCreatedOn(Instant.now());
    }

}
