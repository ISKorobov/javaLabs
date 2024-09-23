package org.ISKor.controller.controllers;

import org.ISKor.controller.startDto.OwnerStartDto;
import org.ISKor.controller.dto.KittyDto;
import org.ISKor.controller.dto.OwnerDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ISKor.controller.services.OwnerServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/owners/")
public class OwnerControllerImpl {
    private final OwnerServiceImpl ownerServiceImpl;

    @Autowired
    public OwnerControllerImpl(OwnerServiceImpl ownerServiceImpl) {
        this.ownerServiceImpl = ownerServiceImpl;
    }

    @PostMapping()
    public OwnerDto createOwner(@Valid @RequestBody OwnerStartDto ownerDto) {
        return ownerServiceImpl.createOwner(ownerDto.name(), ownerDto.birthDate());
    }

    @GetMapping("{id}")
    public OwnerDto getOwnerById(@PathVariable int id) {
        return ownerServiceImpl.getOwnerById(id);
    }

    @GetMapping("kitties/{id}")
    public List<KittyDto> findAllKitties(@PathVariable int id) {
        return ownerServiceImpl.findAllKitties(id);
    }

    @GetMapping()
    public List<OwnerDto> findAllOwners() {
        return ownerServiceImpl.findAllOwners();
    }

    @DeleteMapping("{id}")
    public String removeOwner(@PathVariable int id) {
        ownerServiceImpl.removeOwner(id);
        return "Owner with id " + id + " was deleted";
    }
}