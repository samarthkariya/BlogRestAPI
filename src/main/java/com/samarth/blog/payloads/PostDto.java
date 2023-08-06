package com.samarth.blog.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Integer postId;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    private String imageName;
    private Date addedDate;
    @NotNull
    private CategoryDto category;
    @NotNull
    private UserDto user;
    private Set<CommentDto> comments = new HashSet<>();

}
