package org.ISKor.controller;

import org.ISKor.controller.dto.KittyDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping()
    public KittyDto createKitty(@Valid @RequestBody KittyStartDto kittyDto) {
        return kittyServiceImpl.createKitty(kittyDto.name(), kittyDto.birthDate(), kittyDto.breed(), kittyDto.colour(), kittyDto.ownerId());
    }

    @GetMapping("{id}")
    public KittyDto getKittyById(@PathVariable int id) {
        return kittyServiceImpl.getKittyById(id);
    }

    @GetMapping("friends/{id}")
    public List<KittyDto> findAllFriends(@PathVariable int id) {
        return kittyServiceImpl.findAllFriends(id);
    }

    @GetMapping()
    public List<KittyDto> findAllKitties() {
        return kittyServiceImpl.findAllKitties();
    }

    @DeleteMapping("{id}")
    public String removeKitty(@PathVariable int id) {
        kittyServiceImpl.removeKitty(id);
        return "Kitty with id " + id + " was deleted";
    }

    @Override
    public List<KittyDto> findKittyByBreed(String breed) {
        return kittyServiceImpl.findKittiesByBreed(breed);
    }

    @Override
    public List<KittyDto> findKittiesByColor(String color) {
        return kittyServiceImpl.findKittiesByColor(color);
    }

    @GetMapping("get")
    public List<KittyDto> findKittyByBreed(@NotBlank(message = "Breed should not be blank") @RequestParam(name = "breed") String breed, @RequestParam(name = "color", defaultValue = "empty") @NotBlank(message = "Color should not be blank") String color) {
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
}