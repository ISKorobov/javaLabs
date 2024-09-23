package org.ISKor.controller.services;

import org.ISKor.controller.dto.KittyDto;
import org.ISKor.controller.entities.Owner;

import java.time.LocalDate;
import java.util.List;

public interface KittyService {
    KittyDto createKitty(String name, LocalDate birthDate, String breedName, String colorName, int ownerId);
    void makeFriends(int kittyId1, int kittyId2);
    void unfriendKitties(int kittyId1, int kittyId2);
    KittyDto getKittyById(int id);
    List<KittyDto> findAllKitties();
    List<KittyDto> findAllOwnersKitties();
    List<KittyDto> findAllFriends(int id);
    void removeKitty(int id);
    List<KittyDto> findKittiesByBreed(String breedName);
    List<KittyDto> findOwnersKittiesByBreed(String breedName);
    List<KittyDto> findKittiesByColor(String colorName);
    List<KittyDto> findOwnersKittiesByColor(String colorName);
    List<KittyDto> findKittiesByColorAndBreed(String colorName, String breedName);
    boolean checkOwner(int id);
    Owner getCurrentOwner();
}