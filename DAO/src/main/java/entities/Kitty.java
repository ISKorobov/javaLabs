package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table (name = "kitties")
public class Kitty {

    // lab22222

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "breed")
    private Breed breed;

    @Column(name = "color")
    private Color color;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Kitty> friends;

    public Kitty(String name, LocalDate birthDate, Breed breed, Color color, Owner owner, List<Kitty> friends) {
        this.name = name;
        this.birthDate = birthDate;
        this.breed = breed;
        this.color = color;
        this.owner = owner;
        this.friends = friends;
    }

    public void addFriend(Kitty friend) {
        Objects.requireNonNull(friend);
        if (!friends.contains(friend)) {
            friends.add(friend);
        }
    }

    public void unfriend(Kitty friend) {
        Objects.requireNonNull(friend);
        friends.remove(friend);
    }
}