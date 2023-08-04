package com.samarth.blog.repositories;

import com.samarth.blog.entity.Category;
import com.samarth.blog.entity.Post;
import com.samarth.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);

    List<Post> findByTitleContaining(String title);
}
