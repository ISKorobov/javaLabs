package org.ISKor.controller.DAO;

import org.ISKor.controller.entities.Breed;
import org.ISKor.controller.entities.Color;
import org.ISKor.controller.entities.Kitty;

import java.util.List;

public interface KittyDao {
    void add(Kitty kitty);
    void update(Kitty kitty);
    void remove(Kitty kitty);
    Kitty getById(int id);
    List<Kitty> getAll();
    List<Kitty> getAllFriends(int id);
    List<Kitty> getByBreed(Breed breed);
    List<Kitty> getByColor(Color color);
}
