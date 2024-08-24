package dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public record OwnerDto(int id, String name, LocalDate birthDate, List<Integer> kittyIds) {
}

