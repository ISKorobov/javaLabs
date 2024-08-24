package services;

import DAO.KittyDao;
import DAO.OwnerDao;
import dto.KittyDto;
import entities.Breed;
import entities.Color;
import entities.Kitty;
import entities.Owner;
import exceptions.KittyException;
import lombok.experimental.ExtensionMethod;
import mappers.KittyMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtensionMethod(KittyMapper.class)
public class KittyServiceImpl implements KittyService {
    private final KittyDao kittyDao;
    private final OwnerDao ownerDao;

    public KittyServiceImpl(KittyDao kittyDao, OwnerDao ownerDao) {
        this.ownerDao = ownerDao;
        this.kittyDao = kittyDao;
    }

    public KittyDto createKitty(String name, LocalDate birthDate, String breedName, String colorName, int ownerId) {
        Owner owner = ownerDao.getById(ownerId);
        if (owner == null) {
            throw KittyException.noSuchOwner(ownerId);
        }

        Breed breed = Arrays.stream(Breed.values())
                .filter(c -> c.name().equals(breedName))
                .findFirst()
                .orElse(null);

        if (breed == null) {
            throw KittyException.noSuchBreed(breedName);
        }

        Color color = Arrays.stream(Color.values())
                .filter(c -> c.name().equals(colorName))
                .findFirst()
                .orElse(null);

        if (color == null) {
            throw KittyException.noSuchColor(colorName);
        }
        Kitty kitty = new Kitty(name, birthDate, breed, color, owner, new ArrayList<>());
        owner.addKitty(kitty);
        kittyDao.add(kitty);
        return kitty.asDto();
    }

    public void makeFriends(int kittyId1, int kittyId2) {
        if (kittyDao.getById(kittyId1) == null) {
            throw KittyException.noSuchKitty(kittyId1);
        }
        if (kittyDao.getById(kittyId2) == null) {
            throw KittyException.noSuchKitty(kittyId2);
        }
        Kitty kitty1 = kittyDao.getById(kittyId1);
        Kitty kitty2 = kittyDao.getById(kittyId2);
        kitty1.addFriend(kitty2);
        kitty2.addFriend(kitty1);
        kittyDao.update(kitty1);
    }

    public void unfriendKitties(int kittyId1, int kittyId2) {
        if (kittyDao.getById(kittyId1) == null) {
            throw KittyException.noSuchKitty(kittyId1);
        }
        if (kittyDao.getById(kittyId2) == null) {
            throw KittyException.noSuchKitty(kittyId2);
        }
        Kitty kitty1 = kittyDao.getById(kittyId1);
        Kitty kitty2 = kittyDao.getById(kittyId2);
        kitty1.unfriend(kitty2);
        kitty2.unfriend(kitty1);
        kittyDao.update(kitty1);
    }

    public KittyDto getKittyById(int id) {
        if (kittyDao.getById(id) == null) {
            throw KittyException.noSuchKitty(id);
        }
        return kittyDao.getById(id).asDto();
    }

    public List<KittyDto> findAllKitties() {
        List<KittyDto> kitties = new ArrayList<>();
        for (Kitty kitty : kittyDao.getAll()) {
            kitties.add(kitty.asDto());
        }
        return kitties;
    }

    public List<KittyDto> findAllFriends(int id) {
        if (kittyDao.getById(id) == null) {
            throw KittyException.noSuchKitty(id);
        }
        List<KittyDto> friends = new ArrayList<>();
        for (Kitty friend : kittyDao.getAllFriends(id)) {
            friends.add(friend.asDto());
        }
        return friends;
    }

    public void removeKitty(int id) {
        if (kittyDao.getById(id) == null) {
            throw KittyException.noSuchKitty(id);
        }
        for (Kitty k : kittyDao.getAllFriends(id)) {
            k.getFriends().remove(kittyDao.getById(id));
        }
        kittyDao.getById(id).getOwner().removeKitty(kittyDao.getById(id));
        kittyDao.remove(kittyDao.getById(id));
    }

    public List<KittyDto> findKittiesByBreed(String breedName) {
        Breed breed = Breed.valueOf(breedName);
        List<KittyDto> kitties = new ArrayList<>();
        for (Kitty kitty : kittyDao.getByBreed(breed)) {
            kitties.add(kitty.asDto());
        }
        return kitties;
    }

    public List<KittyDto> findKittiesByColor(String colorName) {
        Color color = Color.valueOf(colorName);
        List<KittyDto> kitties = new ArrayList<>();
        for (Kitty kitty : kittyDao.getByColor(color)) {
            kitties.add(kitty.asDto());
        }
        return kitties;
    }
}