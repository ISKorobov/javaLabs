package org.ISKor.controller.controllers;

import org.ISKor.controller.dto.UserDto;
import org.ISKor.controller.startDto.UserStartDto;

public interface UserController {
    public UserDto createUser(UserStartDto userDto);
}
