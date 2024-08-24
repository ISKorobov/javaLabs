package services;

import dto.KittyDto;
import dto.OwnerDto;

import java.time.LocalDate;
import java.util.List;

public interface OwnerService {
    OwnerDto createOwner(String name, LocalDate birthDate);
    void addKitty(int ownerId, int kittyId);
    OwnerDto getOwnerById(int id);
    List<KittyDto> findAllKitties(int id);
    List<OwnerDto> findAllOwners();
    void removeOwner(int id);
}