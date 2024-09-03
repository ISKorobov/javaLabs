package Controllers;

import dto.KittyDto;

import java.time.LocalDate;
import java.util.List;

public interface KittyController {
    KittyDto createKitty(String name, LocalDate birthDate, String breed, String color, int ownerId);
    KittyDto getKittyById(int id);
    List<KittyDto> findAllFriends(int id);
    List<KittyDto> findAllKitties();
    void removeKitty(int id);
    List<KittyDto> findKittyByBreed(String breed);
    List<KittyDto> findKittiesByColour(String color);
    void makeFriends(int kittyId1, int kittyId2);
    void unfriendKitties(int kittyId1, int kittyId2);
}
