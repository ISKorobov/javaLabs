package org.ISKor.controller.mappers;

import lombok.experimental.UtilityClass;
import org.ISKor.controller.dto.UserDto;
import org.ISKor.controller.entities.User;

import java.util.Objects;

@UtilityClass
public class UserMapper {
    public UserDto asDto(User user){
        Objects.requireNonNull(user);
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole().toString(),
                user.getOwner().getId()
        );
    }
}
