package org.ISKor.controller;

import org.ISKor.controller.dto.KittyDto;

import java.util.List;

public interface KittyController {
    KittyDto createKitty(KittyStartDto kittyDto);
    KittyDto getKittyById(int id);
    List<KittyDto> findAllFriends(int id);
    List<KittyDto> findAllKitties();
    String removeKitty(int id);
    List<KittyDto> findKittyByBreed(String breed);
    List<KittyDto> findKittiesByColor(String color);
    String makeFriends(int kittyId1, int kittyId2);
    String unfriendKitties(int kittyId1, int kittyId2);
}
