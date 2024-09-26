package org.ISKor.services;

import org.ISKor.controller.dto.KittyDto;
import org.ISKor.controller.entities.Owner;

import java.time.LocalDate;
import java.util.List;

public interface KittyService {
    KittyDto createKitty(String name, LocalDate birthDate, String breedName, String colorName, int ownerId);
    void makeFriends(int kittyId1, int kittyId2);
    void unfriendKitties(int kittyId1, int kittyId2);
    void getKittyById(int id);
    void findAllKitties(int id);
    void findAllFriends(int id);
    void removeKitty(int id);
    void findKittiesByBreed(String breedName);
    void findKittiesByColor(String colorName);
    void findKittiesByColorAndBreed(String colorName, String breedName);
}