package org.ISKor.service;

import org.ISKor.controller.dto.KittyDto;
import org.ISKor.controller.dto.OwnerDto;

import java.time.LocalDate;
import java.util.List;

public interface OwnerService {
    OwnerDto createOwner(String name, LocalDate birthDate);
    void getOwnerById(int id);
    void findAllKitties(int id);
    void findAllOwners();
    void removeOwner(int id);
}