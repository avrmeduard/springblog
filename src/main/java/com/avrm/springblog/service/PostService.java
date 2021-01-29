package com.avrm.springblog.service;

import com.avrm.springblog.dto.PostDto;
import com.avrm.springblog.exception.PostNotFoundException;
import com.avrm.springblog.model.Post;
import com.avrm.springblog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
public class PostService {

    @Autowired
    private AuthService authService;

    @Autowired
    private PostRepository postRepository;

    public Post mapFromPostToDto(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        // we need to find the current user fom AuthService other while throw runtime exception
        User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("No user login"));
        post.setUsername(loggedInUser.getUsername());
        post.setCreatedOn(Instant.now());
        post.setUpdatedOn(Instant.now());
        return post;
    }

    public void createPost(PostDto postDto) {
        Post post = mapFromPostToDto(postDto);
        postRepository.save(post);
    }

}
