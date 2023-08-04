package com.samarth.blog.services.impl;

import com.samarth.blog.entity.Comment;
import com.samarth.blog.entity.Post;
import com.samarth.blog.exceptions.ResourceNotFoundException;
import com.samarth.blog.payloads.CommentDto;
import com.samarth.blog.repositories.CommentRepo;
import com.samarth.blog.repositories.PostRepo;
import com.samarth.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        comment.setContent(commentDto.getContent());

        return modelMapper.map(commentRepo.save(comment), CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));
        commentRepo.delete(comment);
    }
}
