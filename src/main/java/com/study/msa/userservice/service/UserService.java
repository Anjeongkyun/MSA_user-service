package com.study.msa.userservice.service;

import com.study.msa.userservice.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity createUser(UserDto userDto);
}
