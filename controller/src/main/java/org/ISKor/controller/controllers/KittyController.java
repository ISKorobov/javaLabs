package org.ISKor.controller.controllers;

import org.ISKor.controller.startDto.KittyStartDto;
import org.ISKor.controller.dto.KittyDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface KittyController {
    KittyDto createKitty(KittyStartDto kittyDto);
    ResponseEntity<KittyDto> getKittyById(int id);
    ResponseEntity<List<KittyDto>> findAllFriends(int id);
    List<KittyDto> findAllKitties();
    String removeKitty(int id);
    List<KittyDto> findKittyByBreed(String breed);
    List<KittyDto> findKittiesByColor(String color);
    String makeFriends(int kittyId1, int kittyId2);
    String unfriendKitties(int kittyId1, int kittyId2);
}
