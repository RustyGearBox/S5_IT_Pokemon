package edu.api.pokemon.Service;

import org.springframework.stereotype.Service;

import edu.api.pokemon.Enums.PokemonActions;
import edu.api.pokemon.Exception.Custom.PokemonNotFoundException;
import edu.api.pokemon.Model.Pokemon;
import edu.api.pokemon.Model.User;
import edu.api.pokemon.Model.Request.PokemonRequest;
import edu.api.pokemon.Model.Response.PokemonResponse;
import edu.api.pokemon.Repository.PokemonRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateService {
    
    private final AuthService authService;
    private final PokemonMapper pokemonMapper;
    private final PokemonRepository pokemonRepository;

    public PokemonResponse updatePokemon (PokemonRequest pokemonRequest, PokemonActions action){
        Pokemon pokemon = verifyOwner(pokemonRequest.getUserId());
        switch (action) {
            case FEED -> pokemon.feed(pokemon);
            case PLAY -> pokemon.play(pokemon);
            case CUSTOMIZE -> pokemon.customize(pokemon);
            case SLEEP -> pokemon.sleep(pokemon);
            default -> throw new PokemonNotFoundException("Invalid action: " + action);
        }

        Pokemon savedPokemon = pokemonRepository.save(pokemon);
        return pokemonMapper.toResponse(savedPokemon);

    }

    private Pokemon verifyOwner(int id) {
        User user = authService.getAuthenticatedUser();
        Pokemon pokemon = pokemonRepository.findById(id)
                .orElseThrow(() -> new PokemonNotFoundException("Pokemon not found with ID: " + id));

        pokemon.verifyAdminOrOwner(user, authService);
        return pokemon;
    }

    

}
