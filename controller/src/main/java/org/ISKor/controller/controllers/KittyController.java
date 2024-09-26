package org.ISKor.controller.controllers;

import org.ISKor.controller.dto.KittyDto;
import org.ISKor.controller.dto.KittyStartDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface KittyController {
    void createKitty(KittyStartDto kittyDto);
    KittyDto getKittyById(int id) throws InterruptedException;
   List<KittyDto> findAllFriends(int id) throws InterruptedException;
    List<KittyDto> findAllKitties() throws InterruptedException;
    String removeKitty(int id);
    List<KittyDto> findKittyByBreed(String breed) throws InterruptedException;
    List<KittyDto> findKittiesByColor(String color) throws InterruptedException;
    List<KittyDto> findKittiesByColorAndBreed(String color, String breedName) throws InterruptedException;
    String makeFriends(int kittyId1, int kittyId2);
    String unfriendKitties(int kittyId1, int kittyId2);
}
