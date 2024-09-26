package org.ISKor.controller.controllers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.Valid;
import org.ISKor.controller.dto.*;
import org.ISKor.controller.exceptions.KittyException;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.ISKor.controller.services.KittyServiceImpl;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/kitties/")
public class KittyControllerImpl implements KittyController {
    private final KittyServiceImpl kittyServiceImpl;
    private final KafkaTemplate<String, KittyStartDto> createKitty;
    private final KafkaTemplate<String, Integer> getKitty;
    private final KafkaTemplate<String, KittyFriendsDto> friends;
    private final KafkaTemplate<String, FilterDto> filters;
    private final Consumer<String, KittyListDto> consumerKitties;
    private final Consumer<String, KittyListDto> consumerFriends;
    private final Consumer<String, KittyListDto> consumerFiltered;
    private final Consumer<String, KittyDto> consumer;

    @Autowired
    public KittyControllerImpl(
            KittyServiceImpl kittyServiceImpl,
            KafkaTemplate<String, KittyStartDto> createKitty,
            KafkaTemplate<String, Integer> getKitty,
            KafkaTemplate<String, KittyFriendsDto> friends,
            KafkaTemplate<String, FilterDto> filters,
            @Qualifier("CKF")Consumer<String, KittyListDto> consumerKitties,
            @Qualifier("CKFF")Consumer<String, KittyListDto> consumerFriends,
            @Qualifier("CFF")Consumer<String, KittyListDto> consumerFiltered,
            Consumer<String, KittyDto> consumer) {
        this.kittyServiceImpl = kittyServiceImpl;
        this.createKitty = createKitty;
        this.getKitty = getKitty;
        this.friends = friends;
        this.filters = filters;
        this.consumerKitties = consumerKitties;
        this.consumerFriends = consumerFriends;
        this.consumerFiltered = consumerFiltered;
        this.consumer = consumer;
    }

    @PostMapping()
    public void createKitty(@Valid @RequestBody KittyStartDto kittyDto)  throws KittyException {
        createKitty.send("create_kitty", kittyDto);
    }

    @GetMapping("{id}")
    public KittyDto getKittyById(@PathVariable("id") int id) throws InterruptedException {
        getKitty.send("get_by_id_kitty", id);
        TimeUnit.MILLISECONDS.sleep(1000);
        ConsumerRecords<String, KittyDto> consumerRecords = consumer.poll(Duration.ofMillis(2000));
        Iterator<ConsumerRecord<String, KittyDto>> iterator = consumerRecords.iterator();
        if (iterator.hasNext()) {
            KittyDto value = iterator.next().value();
            consumer.commitSync();
            return value;
        } else {
            return getKittyById(id);
        }
    }

    @GetMapping("friends/{id}")
    public List<KittyDto> findAllFriends(@PathVariable("id") int id) throws InterruptedException {
        getKitty.send("get_by_id_friends", id);
        TimeUnit.MILLISECONDS.sleep(1000);
        ConsumerRecords<String, KittyListDto> consumerRecords = consumerFriends.poll(Duration.ofMillis(2000));
        Iterator<ConsumerRecord<String, KittyListDto>> iterator = consumerRecords.iterator();
        if (iterator.hasNext()) {
            KittyListDto value = iterator.next().value();
            consumerFriends.commitSync();
            return value.kitties();
        } else {
            return findAllFriends(id);
        }
    }

    @GetMapping()
    public List<KittyDto> findAllKitties() throws InterruptedException {
        getKitty.send("get_kitties", 0);
        TimeUnit.MILLISECONDS.sleep(1000);
        ConsumerRecords<String, KittyListDto> consumerRecords = consumerKitties.poll(Duration.ofMillis(2000));
        Iterator<ConsumerRecord<String, KittyListDto>> iterator = consumerRecords.iterator();
        if (iterator.hasNext()) {
            KittyListDto value = iterator.next().value();
            consumerKitties.commitSync();
            return value.kitties();
        } else {
            return findAllKitties();
        }
    }

    @DeleteMapping("{id}")
    public String removeKitty(@PathVariable int id) throws KittyException {
        getKitty.send("remove_kitty", id);
        return "Kitty with id " + id + " was deleted";
    }

    @GetMapping("getBreed")
    public List<KittyDto> findKittyByBreed(@RequestParam(name = "breed") String breedName) throws InterruptedException {
        filters.send("get_kitties_by_breed", new FilterDto(breedName, null));
        TimeUnit.MILLISECONDS.sleep(1000);
        ConsumerRecords<String, KittyListDto> consumerRecords = consumerFiltered.poll(Duration.ofMillis(2000));
        Iterator<ConsumerRecord<String, KittyListDto>> iterator = consumerRecords.iterator();
        if (iterator.hasNext()) {
            KittyListDto value = iterator.next().value();
            consumerFiltered.commitSync();
            return value.kitties();
        } else {
            return findKittyByBreed(breedName);
        }
    }


    @GetMapping("getColor")
    public List<KittyDto> findKittiesByColor(@RequestParam(name = "color") String colorName) throws InterruptedException {
        filters.send("get_kitties_by_colour", new FilterDto(null, colorName));
        TimeUnit.MILLISECONDS.sleep(1000);
        ConsumerRecords<String, KittyListDto> consumerRecords = consumerFiltered.poll(Duration.ofMillis(2000));
        Iterator<ConsumerRecord<String, KittyListDto>> iterator = consumerRecords.iterator();
        if (iterator.hasNext()) {
            KittyListDto value = iterator.next().value();
            consumerFiltered.commitSync();
            return value.kitties();
        } else {
            return findKittiesByColor(colorName);
        }
    }

    @GetMapping("get")
    public List<KittyDto> findKittiesByColorAndBreed(@NotBlank(message = "Breed should not be blank") @RequestParam(name = "breed") String breed, @RequestParam(name = "color", defaultValue = "empty") @NotBlank(message = "Color should not be blank") String color) throws InterruptedException {
        filters.send("get_kitties_by_filters", new FilterDto(breed, color));
        TimeUnit.MILLISECONDS.sleep(1000);
        ConsumerRecords<String, KittyListDto> consumerRecords = consumerFiltered.poll(Duration.ofMillis(2000));
        Iterator<ConsumerRecord<String, KittyListDto>> iterator = consumerRecords.iterator();
        if (iterator.hasNext()) {
            KittyListDto value = iterator.next().value();
            consumerFiltered.commitSync();
            return value.kitties();
        } else {
            return findKittyByBreed(breed);
        }
    }

    @PutMapping("befriend")
    public String makeFriends(@RequestParam(name = "kitty") int kittyId1, @RequestParam(name = "friend") int kittyId2) {
        friends.send("befriend", new KittyFriendsDto(kittyId1, kittyId2));
        return "Kitties " + kittyId1 + " and " + kittyId2 + " are friends now";
    }

    @PutMapping("unfriend")
    public String unfriendKitties(@RequestParam(name = "kitty") int kittyId1, @RequestParam(name = "ex-friend") int kittyId2) {
        friends.send("unfriend", new KittyFriendsDto(kittyId1, kittyId2));
        return "Kitties " + kittyId1 + " and " + kittyId2 + " are not friends anymore";
    }
}