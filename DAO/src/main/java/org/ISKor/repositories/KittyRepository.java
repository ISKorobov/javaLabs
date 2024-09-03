package org.ISKor.repositories;

import org.ISKor.entities.Kitty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KittyRepository extends JpaRepository<Kitty, Integer> {
}
