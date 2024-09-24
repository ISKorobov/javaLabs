package org.ISKor.controller.services;

import lombok.experimental.ExtensionMethod;
import org.ISKor.controller.dto.UserDto;
import org.ISKor.controller.entities.Owner;
import org.ISKor.controller.entities.Role;
import org.ISKor.controller.entities.User;
import org.ISKor.controller.exceptions.UserException;
import org.ISKor.controller.mappers.UserMapper;
import org.ISKor.controller.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ISKor.controller.repositories.OwnerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

@Service
@ExtensionMethod(UserMapper.class)
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, OwnerRepository ownerRepository) {
        this.userRepository = userRepository;
        this.ownerRepository = ownerRepository;
    }

    @Override
    public UserDto createUser(String username, String password, String roleName, int ownerId) {
        if(!ownerRepository.existsById(ownerId)){
            throw UserException.noOwner(ownerId);
        }
        Owner owner = ownerRepository.getReferenceById(ownerId);
        if(userRepository.findUserByUsername(username).isPresent()){
            throw UserException.usernameAlreadyExist(username);
        }
        if (userRepository.findUserByOwner(owner).isPresent()){
            throw UserException.ownerRegistered(ownerId);
        }
        Role role;
        if (Objects.equals(roleName, Role.ADMIN.name())){
            role = Role.ADMIN;
        } else if (Objects.equals(roleName, Role.USER.name())) {
            role = Role.USER;
        } else {
            throw UserException.noRole(roleName);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User(username, encoder.encode(password), role, owner);
        userRepository.save(user);
        return user.asDto();
    }
}
