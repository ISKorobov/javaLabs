package org.ISKor.controller.services;

import org.ISKor.controller.dto.KittyDto;
import org.ISKor.controller.dto.OwnerDto;
import org.ISKor.controller.entities.Kitty;
import org.ISKor.controller.entities.Owner;
import org.ISKor.controller.exceptions.KittyException;
import org.ISKor.controller.exceptions.OwnerException;
import lombok.experimental.ExtensionMethod;
import org.ISKor.controller.mappers.KittyMapper;
import org.ISKor.controller.mappers.OwnerMapper;
import org.ISKor.controller.repositories.KittyRepository;
import org.ISKor.controller.repositories.OwnerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@ExtensionMethod({OwnerMapper.class, KittyMapper.class})
public class OwnerServiceImpl implements OwnerService {
    private final KittyRepository kittyRepository;
    private final OwnerRepository ownerRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository, KittyRepository kittyRepository) {
        this.ownerRepository = ownerRepository;
        this.kittyRepository = kittyRepository;
    }

    @Override
    public OwnerDto createOwner(String name, LocalDate birthDate) {
        Owner owner = new Owner(name, birthDate, new ArrayList<>());
        ownerRepository.save(owner);
        return owner.asDto();
    }

    @Override
    public OwnerDto getOwnerById(int id) {
        if (!ownerRepository.existsById(id)) {
            throw OwnerException.noSuchOwner(id);
        }
        return ownerRepository.getReferenceById(id).asDto();
    }

    @Override
    public List<KittyDto> findAllKitties(int id) {
        if (!ownerRepository.existsById(id)) {
            throw OwnerException.noSuchOwner(id);
        }
        ownerRepository.getReferenceById(id);
        List<KittyDto> kitties = new ArrayList<>();
        for (Kitty kitty : ownerRepository.getReferenceById(id).getKitties()) {
            kitties.add(kitty.asDto());
        }
        return kitties;
    }

    @Override
    public List<OwnerDto> findAllOwners() {
        List<OwnerDto> owners = new ArrayList<>();
        for (Owner owner : ownerRepository.findAll()) {
            owners.add(owner.asDto());
        }
        return owners;
    }

    @Override
    public void removeOwner(int id) {
        if (!ownerRepository.existsById(id)) {
            throw OwnerException.noSuchOwner(id);
        }
        ownerRepository.deleteById(id);
    }

    @Override
    public void addKitty(int ownerId, int catId) {
        if (!ownerRepository.existsById(ownerId)){
            throw OwnerException.noSuchOwner(ownerId);
        }
        var owner = ownerRepository.getReferenceById(ownerId);
        if (!kittyRepository.existsById(catId)) {
            throw KittyException.noSuchKitty(catId);
        }
        var cat = kittyRepository.getReferenceById(catId);
        if (ownerRepository.existsById(cat.getOwner().getId())) {
            var owner_tmp = ownerRepository.getReferenceById(cat.getOwner().getId());
            owner_tmp.getKitties().remove(cat);
            ownerRepository.save(owner_tmp);
        }
        owner.addKitty(cat);
        cat.setOwner(owner);
        ownerRepository.save(owner);
        kittyRepository.save(cat);
    }

    @Override
    public List<KittyDto> getAllCats(int id) {
        if (!ownerRepository.existsById(id)){
            throw OwnerException.noSuchOwner(id);
        }
        ownerRepository.getReferenceById(id);
        List<KittyDto> kitties = new ArrayList<>();
        for (Kitty kitty : ownerRepository.getReferenceById(id).getKitties()) {
            kitties.add(kitty.asDto());
        }
        return kitties;
    }
}
