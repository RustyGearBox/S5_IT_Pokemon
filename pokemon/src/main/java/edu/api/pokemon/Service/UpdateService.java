package edu.api.pokemon.Service;

import org.springframework.stereotype.Service;

import edu.api.pokemon.Enums.PokemonActions;
import edu.api.pokemon.Enums.PokemonRooms;
import edu.api.pokemon.Exception.Custom.PokemonNotFoundException;
import edu.api.pokemon.Model.Pokemon;
import edu.api.pokemon.Model.User;
import edu.api.pokemon.Model.Request.PokemonActionRequest;
import edu.api.pokemon.Model.Response.PokemonResponse;
import edu.api.pokemon.Repository.PokemonRepository;
import edu.api.pokemon.Service.Interface.IUpdateService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateService implements IUpdateService {
    
    private final AuthService authService;
    private final PokemonMapper pokemonMapper;
    private final PokemonRepository pokemonRepository;

    @Override
    public PokemonResponse updatePokemon (PokemonActionRequest pokemonActionRequest) {
        Pokemon pokemon = verifyOwner(pokemonActionRequest);
        PokemonActions action = pokemonActionRequest.getAction();
        switch (action) {
            case FEED -> feed(pokemon);
            case PLAY -> play(pokemon);
            case CUSTOMIZE -> customize(pokemon);
            case SLEEP -> sleep(pokemon);
            default -> throw new PokemonNotFoundException("Invalid action: " + action);
    }

        Pokemon savedPokemon = pokemonRepository.save(pokemon);
        return pokemonMapper.toResponse(savedPokemon);
    }

    @Override
    public Pokemon verifyOwner(PokemonActionRequest pokemonActionRequest) {
        User user = authService.getAuthenticatedUser();
        int pokemonId = pokemonActionRequest.getPokemonId();
        Pokemon pokemon = pokemonRepository.findById(pokemonId)
                .orElseThrow(() -> new PokemonNotFoundException("Pokemon not found with ID: " + pokemonId));

        pokemon.verifyAdminOrOwner(user, authService);
        return pokemon;
    }

        public void feed(Pokemon pokemon) {
        pokemon.setHealth(Math.min(pokemon.getHealth() + 20, 100));
        pokemon.setHappiness(Math.min(pokemon.getHappiness() + 10, 100));
    }

    public void play(Pokemon pokemon) {
        pokemon.setHealth(Math.min(pokemon.getHealth() - 10, 100));
        pokemon.setHappiness(Math.min(pokemon.getHappiness() + 20, 100));
    }

    public void sleep(Pokemon pokemon) {
        pokemon.setHealth(Math.min(pokemon.getHealth() + 100, 100));
    }

    public void customize(Pokemon pokemon) {
        pokemon.setHappiness(Math.min(pokemon.getHappiness() - 50, 100));
        if (pokemon.getRoom() == PokemonRooms.BEDROOM) {
            pokemon.setRoom(PokemonRooms.GARDEN);
        } else {
            pokemon.setRoom(PokemonRooms.BEDROOM);     
        }
    }

}
