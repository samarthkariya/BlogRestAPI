package com.samarth.blog.services.impl;

import com.samarth.blog.entity.Category;
import com.samarth.blog.entity.Post;
import com.samarth.blog.entity.User;
import com.samarth.blog.exceptions.ResourceNotFoundException;
import com.samarth.blog.payloads.CustomResponse;
import com.samarth.blog.payloads.PostDto;
import com.samarth.blog.repositories.CategoryRepo;
import com.samarth.blog.repositories.PostRepo;
import com.samarth.blog.repositories.UserRepo;
import com.samarth.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;
    private final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user Id not found", "userId", userId));
        logger.info("user is here " + user);
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category Id not found", "catetegoryId", categoryId));
        logger.info("category is here " + category);
        Post post = modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setCategory(category);
        post.setUser(user);

        Post nPost = postRepo.save(post);
        logger.info("new post " + nPost);
        return modelMapper.map(nPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
        post.setImageName(postDto.getImageName());

        postRepo.save(post);

        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {

        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        postRepo.delete(post);
    }

    @Override
    public CustomResponse<PostDto> getA11Post(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePost = postRepo.findAll(p);

        List<Post> allPost = pagePost.getContent();
        List<PostDto> postDtos = allPost.stream().map((post) -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        CustomResponse<PostDto> customResponse = new CustomResponse<>();

        customResponse.setContent(postDtos);
        customResponse.setPageNumber(pagePost.getNumber());
        customResponse.setPageSize(pagePost.getSize());
        customResponse.setTotalElements(pagePost.getTotalElements());
        customResponse.setTotalPages(pagePost.getTotalPages());
        customResponse.setLastPage(pagePost.isLast());

        return customResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category Id not found", "catetegoryId", categoryId));
        List<Post> posts = postRepo.findByCategory(category);
        return posts.stream().map((post) -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {

        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user Id not found", "userId", userId));
        List<Post> posts = postRepo.findByUser(user);
        return posts.stream().map((post) -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = postRepo.findByTitleContaining(keyword);

        return posts.stream().map((post) -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }
}
