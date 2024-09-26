package org.ISKor.services;

import lombok.experimental.ExtensionMethod;
import org.ISKor.controller.dto.KittyDto;
import org.ISKor.controller.dto.KittyListDto;
import org.ISKor.controller.entities.*;
import org.ISKor.controller.exceptions.KittyException;
import org.ISKor.controller.mappers.KittyMapper;
import org.ISKor.controller.repositories.KittyRepository;
import org.ISKor.controller.repositories.OwnerRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@ExtensionMethod(KittyMapper.class)
public class KittyServiceImpl implements KittyService {
    private final KittyRepository kittyRepository;
    private final OwnerRepository ownerRepository;
    private final KafkaTemplate<String, KittyDto> getKitty;
    private final KafkaTemplate<String, KittyListDto> getKitties;

    public KittyServiceImpl(KittyRepository kittyRepository, OwnerRepository ownerRepository, KafkaTemplate<String, KittyDto> getKitty, KafkaTemplate<String, KittyListDto> getKitties) {
        this.ownerRepository = ownerRepository;
        this.kittyRepository = kittyRepository;
        this.getKitty = getKitty;
        this.getKitties = getKitties;
    }

    @Transactional
    @KafkaListener(topics = "befriend", groupId = "groupIdBefriend", containerFactory = "friendFactory")
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

    @Transactional
    @KafkaListener(topics = "befriend", groupId = "groupIdBefriend", containerFactory = "friendFactory")
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
        kittyRepository.save(kitty2);
    }

    @Transactional
    @KafkaListener(topics = "unfriend", groupId = "groupIdUnfriend", containerFactory = "friendFactory")
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

    @Transactional
    @KafkaListener(topics = "get_by_id_kitty", groupId = "groupIdGBIDK", containerFactory = "byIdKittyFactory")
    public void getKittyById(int id) {
        if (!kittyRepository.existsById(id)) {
            throw KittyException.noSuchKitty(id);
        }
        var kitty = kittyRepository.getReferenceById(id);
        getKitty.send("got_by_id_kitty", kitty.asDto());
    }

    @Transactional
    @KafkaListener(topics = "get_kitties", groupId = "groupIdGBIDKs", containerFactory = "byIdKittyFactory")
    public void findAllKitties(int id) {
        var kitties = new ArrayList<KittyDto>();
        for (Kitty kitty : kittyRepository.findAll()) {
            kitties.add(kitty.asDto());
        }
        getKitties.send("got_kitties", new KittyListDto(kitties));
    }

    @Transactional
    @KafkaListener(topics = "get_by_id_friends", groupId = "groupIdGBIDKF", containerFactory = "byIdKittyFactory")
    public void findAllFriends(int id) {
        if (!kittyRepository.existsById(id)) {
            throw KittyException.noSuchKitty(id);
        }
        List<KittyDto> friends = new ArrayList<>();
        for (Kitty friend : kittyRepository.getReferenceById(id).getFriends()) {
            friends.add(friend.asDto());
        }
        getKitties.send("got_by_id_friends", new KittyListDto(friends));
    }

    @Transactional
    @KafkaListener(topics = "remove_kitty", groupId = "groupIdRK", containerFactory = "byIdKittyFactory")
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

    @Transactional
    @KafkaListener(topics = "get_kitties_by_breed", groupId = "groupByBreed", containerFactory = "filterFactory")
    public void findKittiesByBreed(String breedName) {
        Breed breed = Breed.valueOf(breedName);
        List<KittyDto> kitties = new ArrayList<>();
        for (Kitty kitty : kittyRepository.findAll()) {
            if (kitty.getBreed().equals(breed)) {
                kitties.add(kitty.asDto());
            }
        }
        getKitties.send("got_kitties_by_filters", new KittyListDto(kitties));
    }

    @Transactional
    @KafkaListener(topics = "get_kitties_by_colour", groupId = "groupByColour", containerFactory = "filterFactory")
    public void findKittiesByColor(String colorName) {
        Color colour = Color.valueOf(colorName);
        List<KittyDto> kitties = new ArrayList<>();
        for (Kitty kitty : kittyRepository.findAll()) {
            if (kitty.getColor().equals(colour)) {
                kitties.add(kitty.asDto());
            }
        }
        getKitties.send("got_kitties_by_filters", new KittyListDto(kitties));
    }

    @Override
    public void findKittiesByColorAndBreed(String colorName, String breedName) {
        Color colour = Color.valueOf(colorName);
        Breed breed = Breed.valueOf(breedName);
        List<KittyDto> kitties = new ArrayList<>();
        for (Kitty kitty : kittyRepository.findAll()) {
            if (kitty.getColor().equals(colour) && kitty.getBreed().equals(breed)) {
                kitties.add(kitty.asDto());
            }
        }
        getKitties.send("got_kitties_by_filters", new KittyListDto(kitties));
    }
}
