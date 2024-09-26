package org.ISKor.mappers;

import org.ISKor.controller.dto.KittyDto;
import org.ISKor.controller.entities.Breed;
import org.ISKor.controller.entities.Color;
import org.ISKor.controller.entities.Kitty;
import org.ISKor.controller.entities.Owner;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@UtilityClass
public class KittyMapper {
    public KittyDto asDto(Kitty kitty) {
        List<Integer> friendsId = kitty.getFriends().stream()
                .map(Kitty::getId)
                .collect(Collectors.toList());

        return new KittyDto(
                kitty.getId(),
                kitty.getName(),
                kitty.getBirthDate(),
                kitty.getBreed().name(),
                kitty.getColor().name(),
                kitty.getOwner().getId(),
                friendsId
        );
    }

    public Kitty fromDto(KittyDto kittyDto, Owner owner, List<Kitty> friends) {
        return new Kitty(
                kittyDto.name(),
                kittyDto.birthDate(),
                Breed.valueOf(kittyDto.breed()),
                Color.valueOf(kittyDto.colour()),
                owner,
                friends
        );
    }
}
