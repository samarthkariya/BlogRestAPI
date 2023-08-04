package com.samarth.blog.repositories;

import com.samarth.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepo extends JpaRepository<Comment, Integer> {
}
