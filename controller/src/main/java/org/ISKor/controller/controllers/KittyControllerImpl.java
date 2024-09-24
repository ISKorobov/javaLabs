package org.ISKor.controller.controllers;

import jakarta.validation.constraints.NotBlank;
import org.ISKor.controller.startDto.KittyStartDto;
import org.ISKor.controller.dto.KittyDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.ISKor.controller.services.KittyServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/kitties/")
public class KittyControllerImpl implements KittyController {
    private final KittyServiceImpl kittyServiceImpl;

    @Autowired
    public KittyControllerImpl(KittyServiceImpl kittyServiceImpl) {
        this.kittyServiceImpl = kittyServiceImpl;
    }

    @Override
    @PostMapping()
    public KittyDto createKitty(@Valid @RequestBody KittyStartDto kittyDto) {
        return kittyServiceImpl.createKitty(kittyDto.name(), kittyDto.birthDate(), kittyDto.breed(), kittyDto.colour(), kittyDto.ownerId());
    }

    @GetMapping("{id}")
    public ResponseEntity<KittyDto> getKittyById(@PathVariable int id) {
        if(isAdmin()){
            return new ResponseEntity<>(kittyServiceImpl.getKittyById(id), HttpStatus.OK);
        }
        if(kittyServiceImpl.checkOwner(id)){
            return new ResponseEntity<>(kittyServiceImpl.getKittyById(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("friends/{id}")
    public ResponseEntity<List<KittyDto>> findAllFriends(@PathVariable int id) {
        if(isAdmin() || kittyServiceImpl.checkOwner(id)){
            return new ResponseEntity<>(kittyServiceImpl.findAllFriends(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping()
    public List<KittyDto> findAllKitties() {
        if(isAdmin()){
            return kittyServiceImpl.findAllKitties();
        }
        return kittyServiceImpl.findAllOwnersKitties();
    }

    @DeleteMapping("{id}")
    public String removeKitty(@PathVariable int id) {
        kittyServiceImpl.removeKitty(id);
        return "Kitty with id " + id + " was deleted";
    }

    @Override
    public List<KittyDto> findKittyByBreed(String breed) {
        if(isAdmin()) {
            return kittyServiceImpl.findKittiesByBreed(breed);
        }
        return kittyServiceImpl.findOwnersKittiesByBreed(breed);
    }

    @Override
    public List<KittyDto> findKittiesByColor(String color) {
        return kittyServiceImpl.findKittiesByColor(color);
    }

    @GetMapping("get")
    public List<KittyDto> findKittiesByColorAndBreed(@NotBlank(message = "Breed should not be blank") @RequestParam(name = "breed") String breed, @RequestParam(name = "color", defaultValue = "empty") @NotBlank(message = "Color should not be blank") String color) {
        if (breed.equals("empty")) {
            return kittyServiceImpl.findKittiesByBreed(breed);
        } else if (color.equals("empty")) {
            return kittyServiceImpl.findKittiesByColor(color);
        } else {
            return kittyServiceImpl.findKittiesByColorAndBreed(color, breed);
        }
    }

    @PutMapping("befriend")
    public String makeFriends(@RequestParam(name = "kitty") int kittyId1, @RequestParam(name = "friend") int kittyId2) {
        kittyServiceImpl.makeFriends(kittyId1, kittyId2);
        return "Kitties " + kittyId1 + " and " + kittyId2 + " are friends now";
    }

    @PutMapping("unfriend")
    public String unfriendKitties(@RequestParam(name = "kitty") int kittyId1, @RequestParam(name = "ex-friend") int kittyId2) {
        kittyServiceImpl.unfriendKitties(kittyId1, kittyId2);
        return "Kitties " + kittyId1 + " and " + kittyId2 + " are not friends anymore";
    }

    private boolean isAdmin(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
    }
}