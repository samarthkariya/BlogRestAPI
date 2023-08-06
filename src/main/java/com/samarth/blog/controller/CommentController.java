package com.samarth.blog.controller;

import com.samarth.blog.payloads.ApiResponse;
import com.samarth.blog.payloads.CommentDto;
import com.samarth.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @DeleteMapping("/delete/{commentId}")
    public ApiResponse deleteComment(@PathVariable("commentId") Integer commentId) {
        commentService.deleteComment(commentId);
        return new ApiResponse("Comment Deleted Successfully!", true);
    }

    @PostMapping("/add/{postId}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable("postId") Integer postId) {
        return new ResponseEntity<>(commentService.createComment(commentDto, postId), HttpStatus.CREATED);
    }
}
