package mappers;

import dto.OwnerDto;
import entities.Kitty;
import entities.Owner;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class OwnerMapper {
    public OwnerDto asDto(Owner owner) {
        List<Integer> kittyIds = owner.getKitties().stream()
                .map(Kitty::getId)
                .collect(Collectors.toList());

        return new OwnerDto(
                owner.getId(),
                owner.getName(),
                owner.getBirthDate(),
                kittyIds
        );
    }

    public Owner fromDto(OwnerDto ownerDto, List<Kitty> kitties) {
        return new Owner(
                ownerDto.name(),
                ownerDto.birthDate(),
                kitties
        );
    }
}
