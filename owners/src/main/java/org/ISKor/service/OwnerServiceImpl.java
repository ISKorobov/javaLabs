package org.ISKor.service;

import lombok.experimental.ExtensionMethod;
import org.ISKor.controller.dto.KittyDto;
import org.ISKor.controller.dto.KittyListDto;
import org.ISKor.controller.dto.OwnerDto;
import org.ISKor.controller.dto.OwnerListDto;
import org.ISKor.controller.entities.Kitty;
import org.ISKor.controller.entities.Owner;
import org.ISKor.controller.exceptions.OwnerException;
import org.ISKor.controller.mappers.KittyMapper;
import org.ISKor.controller.mappers.OwnerMapper;
import org.ISKor.controller.repositories.KittyRepository;
import org.ISKor.controller.repositories.OwnerRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@ExtensionMethod({OwnerMapper.class, KittyMapper.class})
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;
    private final KafkaTemplate<String, OwnerDto> getOwner;
    private final KafkaTemplate<String, OwnerListDto> getOwners;
    private final KafkaTemplate<String, KittyListDto> getKitties;

    public OwnerServiceImpl(OwnerRepository ownerRepository, KittyRepository kittyRepository, KafkaTemplate<String, OwnerDto> getOwner, KafkaTemplate<String, OwnerListDto> getOwners, KafkaTemplate<String, KittyListDto> getKitties) {
        this.ownerRepository = ownerRepository;
        this.getOwner = getOwner;
        this.getOwners = getOwners;
        this.getKitties = getKitties;
    }

    @Transactional
    @KafkaListener(topics = "create_owner", groupId = "groupIdCO", containerFactory = "createOwnerFactory")
    public OwnerDto createOwner(String name, LocalDate birthDate) {
        Owner owner = new Owner(name, birthDate, new ArrayList<>());
        ownerRepository.save(owner);
        return owner.asDto();
    }

    @Transactional
    @KafkaListener(topics = "get_by_id_owner", groupId = "groupIdGBIDO", containerFactory = "byIdOwnerFactory")
    public void getOwnerById(int id) {
        if (!ownerRepository.existsById(id)) {
            throw OwnerException.noSuchOwner(id);
        }
        var owner = ownerRepository.getReferenceById(id);
        getOwner.send("got_by_id_owner", owner.asDto());
    }

    @Transactional
    @KafkaListener(topics = "get_by_id_owners_kitties", groupId = "groupIdGBIDOK", containerFactory = "byIdOwnerFactory")
    public void findAllKitties(int id) {
        if (!ownerRepository.existsById(id)) {
            throw OwnerException.noSuchOwner(id);
        }
        ownerRepository.getReferenceById(id);
        List<KittyDto> kitties = new ArrayList<>();
        for (Kitty kitty : ownerRepository.getReferenceById(id).getKitties()) {
            kitties.add(kitty.asDto());
        }
        getKitties.send("got_by_id_owners_kitties", new KittyListDto(kitties));
    }

    @Transactional
    @KafkaListener(topics = "get_owners", groupId = "groupIdGO", containerFactory = "byIdOwnerFactory")
    public void findAllOwners() {
        List<OwnerDto> owners = new ArrayList<>();
        for (Owner owner : ownerRepository.findAll()) {
            owners.add(owner.asDto());
        }
        getOwners.send("got_owners", new OwnerListDto(owners));
    }

    @Transactional
    @KafkaListener(topics = "remove_owner", groupId = "groupIdRO", containerFactory = "byIdOwnerFactory")
    public void removeOwner(int id) {
        if (!ownerRepository.existsById(id)) {
            throw OwnerException.noSuchOwner(id);
        }
        ownerRepository.deleteById(id);
    }
}
