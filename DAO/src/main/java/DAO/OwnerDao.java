package DAO;

import entities.Kitty;
import entities.Owner;

import java.util.List;

public interface OwnerDao {
    void add(Owner owner);
    void update(Owner owner);
    void remove(Owner owner);
    Owner getById(int id);
    List<Owner> getAll();
    List<Kitty> getAllKitties(int id);
}
