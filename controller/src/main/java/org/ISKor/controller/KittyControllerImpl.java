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

    @Override
    @PostMapping()
    public KittyDto createKitty(@Valid @RequestBody KittyStartDto kittyDto) {
        return kittyServiceImpl.createKitty(kittyDto.name(), kittyDto.birthDate(), kittyDto.breed(), kittyDto.colour(), kittyDto.ownerId());
    }

    @Override
    @GetMapping("{id}")
    public KittyDto getKittyById(@PathVariable int id) {
        return kittyServiceImpl.getKittyById(id);
    }

    @Override
    @GetMapping("friends/{id}")
    public List<KittyDto> findAllFriends(@PathVariable int id) {
        return kittyServiceImpl.findAllFriends(id);
    }

    @Override
    @GetMapping()
    public List<KittyDto> findAllKitties() {
        return kittyServiceImpl.findAllKitties();
    }

    @Override
    @DeleteMapping("{id}")
    public String removeKitty(@PathVariable int id) {
        kittyServiceImpl.removeKitty(id);
        return "Kitty with id " + id + " was deleted";
    }

    @Override
    @GetMapping("getBreed")
    public List<KittyDto> findKittyByBreed(@RequestParam(name = "breed") String breedName) {
        return kittyServiceImpl.findKittiesByBreed(breedName);
    }

    @Override
    @GetMapping("getColor")
    public List<KittyDto> findKittiesByColor(@RequestParam(name = "color") String colorName) {
        return kittyServiceImpl.findKittiesByColor(colorName);
    }

    @Override
    @GetMapping("getColorBreed")
    public List<KittyDto> getCatsByColourAndBreed(@RequestParam(name = "color") String colorName, @RequestParam(name = "breed") String breedName) {
        return kittyServiceImpl.findKittiesByColorAndBreed(colorName, breedName);
    }

    @Override
    @PutMapping("befriend")
    public String makeFriends(@RequestParam(name = "kitty") int kittyId1, @RequestParam(name = "friend") int kittyId2) {
        kittyServiceImpl.makeFriends(kittyId1, kittyId2);
        return "Kitties " + kittyId1 + " and " + kittyId2 + " are friends now";
    }

    @Override
    @PutMapping("unfriend")
    public String unfriendKitties(@RequestParam(name = "kitty") int kittyId1, @RequestParam(name = "ex-friend") int kittyId2) {
        kittyServiceImpl.unfriendKitties(kittyId1, kittyId2);
        return "Kitties " + kittyId1 + " and " + kittyId2 + " are not friends anymore";
    }
}