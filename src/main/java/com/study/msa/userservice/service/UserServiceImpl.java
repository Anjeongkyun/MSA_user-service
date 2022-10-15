package com.study.msa.userservice.service;

import com.study.msa.userservice.domain.UserEntity;
import com.study.msa.userservice.dto.UserDto;
import com.study.msa.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        ModelMapper mp = new ModelMapper();
        mp.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mp.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd("encrypted_password");
        userRepository.save(userEntity);
        return null;
    }
}
