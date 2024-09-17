package Controllers;

import Controllers.OwnerController;
import dto.KittyDto;
import dto.OwnerDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import services.OwnerServiceImpl;

import java.time.LocalDate;
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