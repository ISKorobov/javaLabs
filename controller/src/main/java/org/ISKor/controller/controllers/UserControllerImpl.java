package org.ISKor.controller.controllers;

import jakarta.validation.Valid;
import org.ISKor.controller.dto.UserDto;
import org.ISKor.controller.services.UserServiceImpl;
import org.ISKor.controller.startDto.UserStartDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/")
public class UserControllerImpl implements UserController{
    private final UserServiceImpl userService;

    @Autowired
    public UserControllerImpl(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping()
    @Override
    public UserDto createUser(@Valid @RequestBody UserStartDto userDto) {
        return userService.createUser(userDto.username(), userDto.password(), userDto.roleName(), userDto.ownerId());
    }
}
