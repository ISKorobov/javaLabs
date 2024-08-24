import dto.KittyDto;
import dto.OwnerDto;
import services.OwnerServiceImpl;

import java.time.LocalDate;
import java.util.List;

public class OwnerControllerImpl implements OwnerController{
    private final OwnerServiceImpl ownerService;

    public OwnerControllerImpl(OwnerServiceImpl ownerService) {
        this.ownerService = ownerService;
    }

    @Override
    public OwnerDto createOwner(String name, LocalDate birthDate) {
        return ownerService.createOwner(name, birthDate);
    }

    @Override
    public void addKitty(int ownerId, int kittyId) {
        ownerService.addKitty(ownerId, kittyId);
    }

    @Override
    public OwnerDto getOwnerById(int id) {
        return ownerService.getOwnerById(id);
    }

    @Override
    public List<KittyDto> findAllKitties(int id) {
        return ownerService.findAllKitties(id);
    }

    @Override
    public List<OwnerDto> findAllOwners() {
        return ownerService.findAllOwners();
    }

    @Override
    public void removeOwner(int id) {
        ownerService.removeOwner(id);
    }
}
