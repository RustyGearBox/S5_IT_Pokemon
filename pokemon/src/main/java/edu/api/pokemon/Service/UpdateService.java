package edu.api.pokemon.Service;

import org.springframework.stereotype.Service;

import edu.api.pokemon.Enums.PokemonActions;
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
            case FEED -> pokemon.feed(pokemon);
            case PLAY -> pokemon.play(pokemon);
            case CUSTOMIZE -> pokemon.customize(pokemon);
            case SLEEP -> pokemon.sleep(pokemon);
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

}
