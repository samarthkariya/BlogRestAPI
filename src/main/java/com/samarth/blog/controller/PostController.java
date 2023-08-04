package com.samarth.blog.controller;

import com.samarth.blog.config.AppConstant;
import com.samarth.blog.payloads.ApiResponse;
import com.samarth.blog.payloads.CustomResponse;
import com.samarth.blog.payloads.PostDto;
import com.samarth.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/user/{userId}/category/{categoryId}")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable("userId") Integer userId, @PathVariable("categoryId") Integer categoryId) {
        return new ResponseEntity<>(postService.createPost(postDto, userId, categoryId), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable("userId") Integer userId) {
        return new ResponseEntity<List<PostDto>>(postService.getPostsByUser(userId), HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable("categoryId") Integer categoryId) {
        return new ResponseEntity<List<PostDto>>(postService.getPostsByCategory(categoryId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CustomResponse<PostDto>> getAllPost(@RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
                                                              @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
                                                              @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY_POSTID, required = false) String sortBy,
                                                              @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir) {
        return new ResponseEntity<CustomResponse<PostDto>>(postService.getA11Post(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Integer postId) {
        return new ResponseEntity<PostDto>(postService.getPostById(postId), HttpStatus.OK);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable("postId") Integer postId) {
        return new ResponseEntity<>(postService.updatePost(postDto, postId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{postId}")
    public ApiResponse deletePost(@PathVariable("postId") Integer postId) {
        postService.deletePost(postId);
        return new ApiResponse("Post is Successfully Deleted!!", true);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchByTitle(@PathVariable("keyword") String keyword) {
        return new ResponseEntity<>(postService.searchPosts(keyword), HttpStatus.OK);
    }
}
