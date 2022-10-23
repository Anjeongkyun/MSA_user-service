package com.study.msa.userservice.controller;

import com.study.msa.userservice.domain.UserEntity;
import com.study.msa.userservice.dto.UserDto;
import com.study.msa.userservice.service.UserService;
import com.study.msa.userservice.vo.Greeting;
import com.study.msa.userservice.vo.RequestUser;
import com.study.msa.userservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user-service")
public class UserController {

    private final UserService userService;
    private final Environment env;

    @Autowired
    private Greeting greeting;

    @GetMapping("/health-check")
    public String status() {
        return String.format("it`s working in User Service on Port %s", env.getProperty("local.server.port"));
    }

    @GetMapping("/welcome")
    public String welcome() {
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody RequestUser requestUser){

        ModelMapper mp = new ModelMapper();
        mp.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = mp.map(requestUser, UserDto.class);

        return userService.createUser(userDto);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers(){
        Iterable<UserEntity> userList = userService.getUserByAll();

        List<ResponseUser> result = new ArrayList<>();
        userList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseUser.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId){
        UserDto userDto = userService.getUserByUserId(userId);

        ResponseUser returnVal = new ModelMapper().map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnVal);
    }
}
