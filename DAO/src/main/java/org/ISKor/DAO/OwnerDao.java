package org.ISKor.DAO;

import org.ISKor.entities.Kitty;
import org.ISKor.entities.Owner;

import java.util.List;

public interface OwnerDao {
    void add(Owner owner);
    void update(Owner owner);
    void remove(Owner owner);
    Owner getById(int id);
    List<Owner> getAll();
    List<Kitty> getAllKitties(int id);
}
