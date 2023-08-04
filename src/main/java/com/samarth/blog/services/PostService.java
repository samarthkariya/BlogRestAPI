package com.samarth.blog.services;

import com.samarth.blog.payloads.CustomResponse;
import com.samarth.blog.payloads.PostDto;

import java.util.List;

public interface PostService {

    //create post
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    PostDto updatePost(PostDto postDto, Integer postId);

    void deletePost(Integer postid);

    // get all posts
    CustomResponse<PostDto> getA11Post(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    // get single post
    PostDto getPostById(Integer postld);

    // get all posts by category
    List<PostDto> getPostsByCategory(Integer categoryld);

    // get all posts by user
    List<PostDto> getPostsByUser(Integer userld);

    List<PostDto> searchPosts(String keyword);


}
