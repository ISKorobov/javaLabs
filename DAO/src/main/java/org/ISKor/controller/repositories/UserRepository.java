package org.ISKor.controller.repositories;

import org.ISKor.controller.entities.Owner;
import org.ISKor.controller.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUsername(String username);
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByOwner(Owner owner);
}