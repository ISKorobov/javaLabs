package org.ISKor.controller.DAO;

import org.ISKor.controller.entities.Kitty;
import org.ISKor.controller.entities.Owner;

import java.util.List;

public interface OwnerDao {
    void add(Owner owner);
    void update(Owner owner);
    void remove(Owner owner);
    Owner getById(int id);
    List<Owner> getAll();
    List<Kitty> getAllKitties(int id);
}
