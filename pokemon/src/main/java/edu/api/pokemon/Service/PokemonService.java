package edu.api.pokemon.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edu.api.pokemon.Enums.PokemonRooms;
import edu.api.pokemon.Exception.Custom.PokemonNameExistException;
import edu.api.pokemon.Exception.Custom.PokemonNotFoundException;
import edu.api.pokemon.Model.Pokemon;
import edu.api.pokemon.Model.User;
import edu.api.pokemon.Model.Request.PokemonFindRequest;
import edu.api.pokemon.Model.Request.PokemonRequest;
import edu.api.pokemon.Model.Response.PokemonResponse;
import edu.api.pokemon.Repository.PokemonRepository;
import edu.api.pokemon.Service.Interface.IPokemonService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PokemonService implements IPokemonService {
    
    private final AuthService authService;
    private final PokemonRepository pokemonRepository;

    public PokemonResponse createPokemon(PokemonRequest request) {
        
        User user = authService.getAuthenticatedUser();

        if (user.hasPokemonsWithNickname(request.getNickname())) {
            throw new PokemonNameExistException(request.getNickname());
        }

        Pokemon pokemon = Pokemon.builder()
            .name(request.getName())
            .nickname(request.getNickname())
            .user(user)
            .health(100)
            .happiness(50)
            .experience(0)
            .room(PokemonRooms.LIVING_ROOM)
            .build();

        Pokemon savedPokemon = pokemonRepository.save(pokemon);

        PokemonResponse pokemonResponse = PokemonResponse.builder()
            .id(savedPokemon.getId())
            .name(savedPokemon.getName())
            .nickname(savedPokemon.getNickname())
            .health(savedPokemon.getHealth())
            .happiness(savedPokemon.getHappiness())
            .experience(savedPokemon.getExperience())
            .room(savedPokemon.getRoom())
            .build();

        return pokemonResponse;
    }
    
    public Pokemon getPokemonById(int id) {
        return pokemonRepository.findById(id)
            .orElseThrow(() -> new PokemonNotFoundException(id));
    }
    
    public void deletePokemon(PokemonFindRequest pokemonFindRequest) {
        Pokemon pokemon = getPokemonById(pokemonFindRequest.getId());
        pokemon.verifyAdminOrOwner(authService.getAuthenticatedUser(), authService);
        pokemonRepository.delete(pokemon);
    }
    
    public Page<PokemonResponse> getUserPokemons(Pageable pageable) {
        User user = authService.getAuthenticatedUser();
        Page<Pokemon> pokemonsPage = pokemonRepository.findByUser(user, pageable);
        PokemonMapper pokemonMapper = new PokemonMapper();
        return pokemonsPage.map(pokemonMapper::toResponse);
    }
    
    public Page<PokemonResponse> getAllPokemons(Pageable pageable) {
        return null;
    }
    
}
