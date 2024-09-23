package org.ISKor.controller.controllers;

import org.ISKor.controller.dto.KittyDto;
import org.ISKor.controller.dto.OwnerDto;

import java.time.LocalDate;
import java.util.List;

public interface OwnerController {
    OwnerDto createOwner(String name, LocalDate birthDate);
    void addKitty(int ownerId, int kittyId);
    OwnerDto getOwnerById(int id);
    List<KittyDto> findAllKitties(int id);
    List<OwnerDto> findAllOwners();
    void removeOwner(int id);
}