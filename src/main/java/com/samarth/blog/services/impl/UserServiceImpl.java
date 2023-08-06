package com.samarth.blog.services.impl;

import com.samarth.blog.config.AppConstant;
import com.samarth.blog.entity.Role;
import com.samarth.blog.entity.User;
import com.samarth.blog.exceptions.ResourceNotFoundException;
import com.samarth.blog.payloads.CustomResponse;
import com.samarth.blog.payloads.UserDto;
import com.samarth.blog.repositories.RoleRepo;
import com.samarth.blog.repositories.UserRepo;
import com.samarth.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = roleRepo.findById(AppConstant.NORMAL_ROLE).get();
        user.getRoles().add(role);
        return modelMapper.map(userRepo.save(user), UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        String pass = userDto.getPassword();
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(pass));
        User savedUser = userRepo.save(user);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(user.getAbout());
        User updateuser = userRepo.save(user);
        return modelMapper.map(updateuser, UserDto.class);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public CustomResponse<UserDto> getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> pageUsers = userRepo.findAll(p);
        List<UserDto> userDtos = pageUsers.stream().map((user) -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());

        CustomResponse<UserDto> customResponse = new CustomResponse<>();

        customResponse.setContent(userDtos);
        customResponse.setPageNumber(pageUsers.getNumber());
        customResponse.setPageSize(pageUsers.getSize());
        customResponse.setTotalElements(pageUsers.getTotalElements());
        customResponse.setTotalPages(pageUsers.getTotalPages());
        customResponse.setLastPage(pageUsers.isLast());

        return customResponse;
    }

    @Override
    public void deleteUser(Integer userId) {

        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));
        userRepo.delete(user);
    }
}
