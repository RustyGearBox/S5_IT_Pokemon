package edu.api.pokemon.Model;

import edu.api.pokemon.Enums.PokemonRooms;
import edu.api.pokemon.Service.AuthService;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pokemon")
public class Pokemon {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder.Default
    @Column(nullable = false)
    private int experience = 0;

    @Builder.Default
    @Column(nullable = false)
    private int happiness = 50;

    @Builder.Default
    @Column(nullable = false)
    private int health = 100;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PokemonRooms room;

    @Default
    @Column(name = "is_sleeping", nullable = false)
    private boolean sleeping = false;

    public void verifyAdminOrOwner(User user, AuthService authService) {
        if (!authService.isAdmin(user) && !this.user.equals(user)) {
            throw new SecurityException("Unauthorized to access this pet.");
        }
    }

    public void feed(Pokemon pokemon) {
        pokemon.setHealth(Math.min(pokemon.getHealth() + 20, 100));
        pokemon.setHappiness(Math.min(pokemon.getHappiness() + 10, 100));
    }

    public void play(Pokemon pokemon) {
        pokemon.setHealth(Math.min(pokemon.getHealth() - 10, 100));
        pokemon.setHappiness(Math.min(pokemon.getHappiness() + 20, 100));
    }

    public void customize(Pokemon pokemon) {
        pokemon.setHappiness(Math.min(pokemon.getHappiness() - 30, 100));
        if (pokemon.getRoom()==PokemonRooms.BEDROOM) {
            pokemon.setRoom(PokemonRooms.GARDEN);
        } else {
            pokemon.setRoom(PokemonRooms.BEDROOM);     
        }
    
    }

    public void sleep(Pokemon pokemon) {
        pokemon.setSleeping(true);
    }

    public void wakeUp(Pokemon pokemon) {
        pokemon.setSleeping(false);
    }
}
