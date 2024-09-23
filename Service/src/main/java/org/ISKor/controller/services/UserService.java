package org.ISKor.controller.services;

import org.ISKor.controller.dto.UserDto;

public interface UserService {
    UserDto createUser(String username, String password, String roleName, int ownerId);
}
