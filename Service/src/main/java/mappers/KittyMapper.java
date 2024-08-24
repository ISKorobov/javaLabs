package mappers;

import dto.KittyDto;
import entities.Breed;
import entities.Color;
import entities.Kitty;
import entities.Owner;
import lombok.experimental.UtilityClass;

import java.util.List;
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
