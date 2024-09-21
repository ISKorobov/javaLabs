package org.ISKor.controller.services;

import org.ISKor.controller.dto.KittyDto;
import org.ISKor.controller.entities.Breed;
import org.ISKor.controller.entities.Color;
import org.ISKor.controller.entities.Kitty;
import org.ISKor.controller.exceptions.KittyException;
import lombok.experimental.ExtensionMethod;
import org.ISKor.controller.mappers.KittyMapper;
import org.ISKor.controller.repositories.KittyRepository;
import org.ISKor.controller.repositories.OwnerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@ExtensionMethod(KittyMapper.class)
public class KittyServiceImpl implements KittyService {
    private final KittyRepository kittyRepository;
    private final OwnerRepository ownerRepository;

    public KittyServiceImpl(KittyRepository kittyRepository, OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
        this.kittyRepository = kittyRepository;
    }

    public KittyDto createKitty(String name, LocalDate birthDate, String breedName, String colorName, int ownerId) {
        if (!ownerRepository.existsById(ownerId)) {
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

        Kitty kitty = new Kitty(name, birthDate, breed, color, ownerRepository.findById(ownerId).orElseThrow(), new ArrayList<>());
        ownerRepository.findById(ownerId).ifPresent(owner -> owner.addKitty(kitty));
        kittyRepository.save(kitty);
        return kitty.asDto();
    }

    public void makeFriends(int id1, int id2) {
        if (!kittyRepository.existsById(id1)) {
            throw KittyException.noSuchKitty(id1);
        }
        if (!kittyRepository.existsById(id2)) {
            throw KittyException.noSuchKitty(id2);
        }
        Kitty kitty1 = kittyRepository.getReferenceById(id1);
        Kitty kitty2 = kittyRepository.getReferenceById(id2);
        kitty1.addFriend(kitty2);
        kitty2.addFriend(kitty1);
        kittyRepository.save(kitty1);
    }

    public void unfriendKitties(int id1, int id2) {
        if (!kittyRepository.existsById(id1)) {
            throw KittyException.noSuchKitty(id1);
        }
        if (!kittyRepository.existsById(id2)) {
            throw KittyException.noSuchKitty(id2);
        }
        Kitty kitty1 = kittyRepository.getReferenceById(id1);
        Kitty kitty2 = kittyRepository.getReferenceById(id2);
        kitty1.unfriend(kitty2);
        kitty2.unfriend(kitty1);
        kittyRepository.save(kitty1);
    }

    public KittyDto getKittyById(int id) {
        if (!kittyRepository.existsById(id)) {
            throw KittyException.noSuchKitty(id);
        }
        return kittyRepository.getReferenceById(id).asDto();
    }

    public List<KittyDto> findAllKitties() {
        List<KittyDto> kitties = new ArrayList<>();
        for (Kitty kitty : kittyRepository.findAll()) {
            kitties.add(kitty.asDto());
        }
        return kitties;
    }

    public List<KittyDto> findAllFriends(int id) {
        if (!kittyRepository.existsById(id)) {
            throw KittyException.noSuchKitty(id);
        }
        List<KittyDto> friends = new ArrayList<>();
        for (Kitty friend : kittyRepository.getReferenceById(id).getFriends()) {
            friends.add(friend.asDto());
        }
        return friends;
    }

    public void removeKitty(int id) {
        if (!kittyRepository.existsById(id)) {
            throw KittyException.noSuchKitty(id);
        }
        Kitty kitty = kittyRepository.findById(id).orElseThrow();
        for (Kitty k : kitty.getFriends()) {
            k.getFriends().remove(kitty);
            kittyRepository.save(k);
        }
        kitty.getFriends().clear();
        kittyRepository.findById(id).orElseThrow().getOwner().removeKitty(kitty);
        kittyRepository.save(kitty);
        kittyRepository.deleteById(id);
    }

    public List<KittyDto> findKittiesByBreed(String breedName) {
        Breed breed = Breed.valueOf(breedName);
        List<KittyDto> kitties = new ArrayList<>();
        for (Kitty kitty : kittyRepository.findAll()) {
            if (kitty.getBreed().equals(breed)) {
                kitties.add(kitty.asDto());
            }
        }
        return kitties;
    }

    public List<KittyDto> findKittiesByColor(String colorName) {
        Color colour = Color.valueOf(colorName);
        List<KittyDto> kitties = new ArrayList<>();
        for (Kitty kitty : kittyRepository.findAll()) {
            if (kitty.getColor().equals(colour)) {
                kitties.add(kitty.asDto());
            }
        }
        return kitties;
    }

    @Override
    public List<KittyDto> findKittiesByColorAndBreed(String colorName, String breedName) {
        Color colour = Color.valueOf(colorName);
        Breed breed = Breed.valueOf(breedName);
        List<KittyDto> kitties = new ArrayList<>();
        for (Kitty kitty : kittyRepository.findAll()) {
            if (kitty.getColor().equals(colour) && kitty.getBreed().equals(breed)) {
                kitties.add(kitty.asDto());
            }
        }
        return kitties;
    }
}