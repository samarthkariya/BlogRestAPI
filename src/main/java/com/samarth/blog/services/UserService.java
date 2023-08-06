package com.samarth.blog.services;

import com.samarth.blog.payloads.CustomResponse;
import com.samarth.blog.payloads.UserDto;

public interface UserService {

    UserDto registerNewUser(UserDto userDto);
    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, Integer userId);

    UserDto getUserById(Integer userId);

    CustomResponse<UserDto> getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    void deleteUser(Integer userId);


}
