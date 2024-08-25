package services;

import DAO.KittyDao;
import DAO.OwnerDao;
import dto.KittyDto;
import dto.OwnerDto;
import entities.Kitty;
import entities.Owner;
import exceptions.OwnerException;
import lombok.experimental.ExtensionMethod;
import mappers.KittyMapper;
import mappers.OwnerMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtensionMethod({OwnerMapper.class, KittyMapper.class})
public class OwnerServiceImpl implements OwnerService {
    private final KittyDao kittyDao;
    private final OwnerDao ownerDao;

    public OwnerServiceImpl(KittyDao kittyDao, OwnerDao ownerDao) {
        this.kittyDao = kittyDao;
        this.ownerDao = ownerDao;
    }

    public OwnerDto createOwner(String name, LocalDate birthDate) {
        Owner owner = new Owner(name, birthDate, new ArrayList<>());
        ownerDao.add(owner);
        return owner.asDto();
    }

    public void addKitty(int ownerId, int kittyId) {
        Owner owner = ownerDao.getById(ownerId);
        Kitty kitty = kittyDao.getById(kittyId);
        if (owner == null) {
            throw OwnerException.noSuchOwner(ownerId);
        }
        if (kitty == null) {
            throw OwnerException.kittyAlreadyExistsException(kittyId);
        }
        owner.addKitty(kitty);
        kitty.setOwner(owner);
        ownerDao.update(owner);
        kittyDao.update(kitty);
    }

    public OwnerDto getOwnerById(int id) {
        Owner owner = ownerDao.getById(id);
        if (owner == null) {
            throw OwnerException.noSuchOwner(id);
        }
        return owner.asDto();
    }

    public List<KittyDto> findAllKitties(int id) {
        if (ownerDao.getById(id) == null) {
            throw OwnerException.noSuchOwner(id);
        }
        List<KittyDto> kitties = new ArrayList<>();
        for (Kitty kitty : ownerDao.getAllKitties(id)) {
            kitties.add(kitty.asDto());
        }
        return kitties;
    }

    public List<OwnerDto> findAllOwners() {
        List<OwnerDto> owners = new ArrayList<>();
        for (Owner owner : ownerDao.getAll()) {
            owners.add(owner.asDto());
        }
        return owners;
    }

    public void removeOwner(int id) {
        if (ownerDao.getById(id) == null) {
            throw OwnerException.noSuchOwner(id);
        }
        ownerDao.remove(ownerDao.getById(id));
    }
}
