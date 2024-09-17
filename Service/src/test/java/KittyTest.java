import org.ISKor.DAO.KittyDao;
import org.ISKor.DAO.OwnerDao;
import dto.KittyDto;
import org.ISKor.entities.Breed;
import org.ISKor.entities.Color;
import org.ISKor.entities.Kitty;
import org.ISKor.entities.Owner;
import exceptions.KittyException;
import exceptions.OwnerException;
import org.ISKor.repositories.KittyRepository;
import org.ISKor.repositories.OwnerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import services.KittyServiceImpl;
import services.OwnerServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import static org.mockito.Mockito.*;

class KittyTest {
    @Mock
    private KittyRepository kittyDao;

    @Mock
    private OwnerRepository ownerDAO;

    @InjectMocks
    private KittyServiceImpl kittyService;

    @InjectMocks
    private OwnerServiceImpl ownerService;

    @BeforeEach
    void before() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOwner() throws KittyException, OwnerException {
        doNothing().when(ownerDAO).save(any(Owner.class));

        ownerService.createOwner("Vanya", LocalDate.of(2003, 10, 15));

        verify(ownerDAO).save(any(Owner.class));
    }

    @Test
    void createCat() throws KittyException, OwnerException {
        doNothing().when(kittyDao).save(any(Kitty.class));

        Owner owner = new Owner("Vanya", LocalDate.of(2003, 10, 15), new ArrayList<>());
        when(ownerDAO.getById(1)).thenReturn(owner);
        KittyDto testKitty = kittyService.createKitty("Kitty", LocalDate.of(2020, 1,1), "SPHYNX", "ORANGE", 1);;

        verify(kittyDao).save(any(Kitty.class));
        verify(ownerDAO).getById(1);
    }

    @Test
    void createCatAndCheckConnectedToOwner() throws KittyException, OwnerException {
        doNothing().when(kittyDao).save(any(Kitty.class));

        Owner owner = new Owner("Vanya", LocalDate.of(2003, 10, 15), new ArrayList<>());
        owner.setId(1);
        when(ownerDAO.getById(1)).thenReturn(owner);
        KittyDto testKitty = kittyService.createKitty("Kitty", LocalDate.of(2020, 1,1), "SPHYNX", "ORANGE", 1);

        verify(kittyDao).save(any(Kitty.class));
        verify(ownerDAO).getById(1);
        Assertions.assertEquals(1, owner.getKitties().size());
        Assertions.assertEquals(owner.getKitties().get(0).getId(), testKitty.id());
        Assertions.assertEquals(owner.getId(), testKitty.ownerId());
    }

    @Test
    void addFriendsForCatAndCheckAllFriendsWasAdded() throws KittyException, OwnerException {
        doNothing().when(kittyDao).save(any(Kitty.class));

        Owner owner = new Owner("Vanya", LocalDate.of(2003, 10, 15), new ArrayList<>());
        Kitty kitty1 = new Kitty("Kot", LocalDate.of(2020, 1, 1), Breed.SIAMESE, Color.BLACK, owner, new ArrayList<>());
        Kitty kitty2 = new Kitty("Kitty", LocalDate.of(2020, 1, 1), Breed.SPHYNX, Color.CALICO, owner, new ArrayList<>());
        Kitty kitty3 = new Kitty("Cat", LocalDate.of(2020, 1, 1), Breed.BENGAL, Color.BICOLOR, owner, new ArrayList<>());

        kitty1.setId(1);
        kitty2.setId(2);
        kitty3.setId(3);

        when(kittyDao.getById(kitty1.getId())).thenReturn(kitty1);
        when(kittyDao.getById(kitty2.getId())).thenReturn(kitty2);
        when(kittyDao.getById(kitty3.getId())).thenReturn(kitty3);

        kittyService.makeFriends(kitty1.getId(), kitty2.getId());
        kittyService.makeFriends(kitty1.getId(), kitty3.getId());

        Assertions.assertEquals(kitty2.getFriends().size(), 1);
        Assertions.assertEquals(kitty3.getFriends().size(), 1);
        Assertions.assertEquals(kitty1.getFriends().size(), 2);

        Assertions.assertEquals(kitty2.getFriends().get(0).getId(), kitty1.getId());
        Assertions.assertEquals(kitty3.getFriends().get(0).getId(), kitty1.getId());
        Assertions.assertEquals(kitty1.getFriends().get(0).getId(), kitty2.getId());
        Assertions.assertEquals(kitty1.getFriends().get(1).getId(), kitty3.getId());
    }

    @Test
    void unfriendFriendsForCatAndCheckAllFriendsWasAdded() throws KittyException, OwnerException {
        doNothing().when(kittyDao).save(any(Kitty.class));

        Owner owner = new Owner("Vanya", LocalDate.of(2003, 10, 15), new ArrayList<>());
        Kitty kitty1 = new Kitty("Kitty1", LocalDate.of(2020, 1, 1), Breed.MAINE_COON, Color.GRAY, owner, new ArrayList<>());
        Kitty kitty2 = new Kitty("Kitty2", LocalDate.of(2020, 1, 1), Breed.MAINE_COON, Color.GRAY, owner, new ArrayList<>());
        kitty1.setId(1);
        kitty2.setId(2);

        when(kittyDao.getById(kitty1.getId())).thenReturn(kitty1);
        when(kittyDao.getById(kitty2.getId())).thenReturn(kitty2);

        kittyService.makeFriends(kitty1.getId(), kitty2.getId());

        Assertions.assertEquals( 1, kitty1.getFriends().size());
        Assertions.assertEquals(1, kitty1.getFriends().size());

        Assertions.assertEquals(kitty1.getFriends().get(0).getId(), kitty2.getId());
        Assertions.assertEquals(kitty2.getFriends().get(0).getId(), kitty1.getId());

        kittyService.unfriendKitties(kitty1.getId(), kitty2.getId());

        Assertions.assertEquals(0, kitty1.getFriends().size());
        Assertions.assertEquals(0, kitty2.getFriends().size());
    }

    @Test
    void addOwnerForCatAndOwnerShouldAdd() throws KittyException, OwnerException {
        doNothing().when(kittyDao).save(any(Kitty.class));
        doNothing().when(ownerDAO).save(any(Owner.class));

        Owner owner1 = new Owner("Vanya", LocalDate.of(2003, 10, 15), new ArrayList<>());
        Owner owner2 = new Owner("Kirill", LocalDate.of(2001, 1, 1), new ArrayList<>());
        owner1.setId(1);
        owner2.setId(2);
        Kitty kitty = new Kitty("Kitty", LocalDate.of(2020, 1, 1), Breed.PERSIAN, Color.WHITE, owner1, new ArrayList<>());
        kitty.setId(1);

        when(kittyDao.getById(kitty.getId())).thenReturn(kitty);
        when(ownerDAO.getById(owner1.getId())).thenReturn(owner1);
        when(ownerDAO.getById(owner2.getId())).thenReturn(owner2);

        ownerService.addKitty(owner2.getId(), kitty.getId());

        Assertions.assertEquals(1, owner2.getKitties().size());
        Assertions.assertEquals(kitty.getId(), owner2.getKitties().get(0).getId());
        Assertions.assertEquals(owner2, kitty.getOwner());
    }
}
