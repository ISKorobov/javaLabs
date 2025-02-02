package services;

import dto.KittyDto;

import java.time.LocalDate;
import java.util.List;

public interface KittyService {
    KittyDto createKitty(String name, LocalDate birthDate, String breedName, String colorName, int ownerId);
    void makeFriends(int kittyId1, int kittyId2);
    void unfriendKitties(int kittyId1, int kittyId2);
    KittyDto getKittyById(int id);
    List<KittyDto> findAllKitties();
    List<KittyDto> findAllFriends(int id);
    void removeKitty(int id);
    List<KittyDto> findKittiesByBreed(String breedName);
    List<KittyDto> findKittiesByColor(String colorName);
}