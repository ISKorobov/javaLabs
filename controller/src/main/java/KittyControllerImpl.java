import dto.KittyDto;
import services.KittyServiceImpl;

import java.time.LocalDate;
import java.util.List;

public class KittyControllerImpl implements KittyController {

    private final KittyServiceImpl kittyServiceImpl;

    public KittyControllerImpl(KittyServiceImpl kittyServiceImpl) {
        this.kittyServiceImpl = kittyServiceImpl;
    }

    @Override
    public KittyDto createKitty(String name, LocalDate birthDate, String breed, String color, int ownerId) {
        return kittyServiceImpl.createKitty(name, birthDate, breed, color, ownerId);
    }

    @Override
    public KittyDto getKittyById(int id) {
        return kittyServiceImpl.getKittyById(id);
    }

    @Override
    public List<KittyDto> findAllFriends(int id) {
        return kittyServiceImpl.findAllFriends(id);
    }

    @Override
    public List<KittyDto> findAllKitties() {
        return kittyServiceImpl.findAllKitties();
    }

    @Override
    public void removeKitty(int id) {
        kittyServiceImpl.removeKitty(id);
    }

    @Override
    public List<KittyDto> findKittyByBreed(String breed) {
        return kittyServiceImpl.findKittiesByBreed(breed);
    }

    @Override
    public List<KittyDto> findKittiesByColour(String color) {
        return kittyServiceImpl.findKittiesByColor(color);
    }

    @Override
    public void makeFriends(int kittyId1, int kittyId2) {
        kittyServiceImpl.makeFriends(kittyId1, kittyId2);
    }

    @Override
    public void unfriendKitties(int kittyId1, int kittyId2) {
        kittyServiceImpl.unfriendKitties(kittyId1, kittyId2);
    }
}
