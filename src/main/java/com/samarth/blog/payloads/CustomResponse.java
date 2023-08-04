package com.samarth.blog.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomResponse<T> {

    private List<T> content;
    private int pageSize;
    private int pageNumber;
    private long totalElements;
    private int totalPages;

    private boolean lastPage;

}
