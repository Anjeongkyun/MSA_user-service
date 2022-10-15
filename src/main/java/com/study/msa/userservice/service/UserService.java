package com.study.msa.userservice.service;

import com.study.msa.userservice.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);
}
