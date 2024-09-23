package org.ISKor.controller.mappers;

import org.ISKor.controller.dto.UserDto;
import org.ISKor.controller.entities.User;

import java.util.Objects;

public class UserMapper {
    public UserDto castDto(User user){
        Objects.requireNonNull(user);
        return new UserDto(user.getId(), user.getUsername(), user.getPassword(), user.getRole().toString(), user.getOwner().getId());
    }
}
