package edu.api.pokemon.Service;

import java.util.List;

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
import edu.api.pokemon.Model.Response.UserPokemonResponse;
import edu.api.pokemon.Repository.PokemonRepository;
import edu.api.pokemon.Service.Interface.IPokemonService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PokemonService implements IPokemonService {
    
    private final AuthService authService;
    private final PokemonMapper pokemonMapper;
    private final PokemonRepository pokemonRepository;

    public PokemonResponse createPokemon(PokemonRequest request) {
        
        User user = authService.getAuthenticatedUser();

        if (user.hasPokemonsWithNickname(request.getNickname())) {
            throw new PokemonNameExistException(request.getNickname());
        }

        Pokemon pokemon = pokemonMapper.toEntity(request, user, PokemonRooms.LIVING_ROOM);
        Pokemon savedPokemon = pokemonRepository.save(pokemon);

        return pokemonMapper.toResponse(savedPokemon);
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
    
    public PokemonResponse getPokemon (int id) {
        Pokemon pokemon = getPokemonById(id);
        pokemon.verifyAdminOrOwner(authService.getAuthenticatedUser(), authService);
        return pokemonMapper.toResponse(pokemon);
    }

    public Page<PokemonResponse> getUserPokemons(Pageable pageable) {
        User user = authService.getAuthenticatedUser();
        Page<Pokemon> pokemonsPage = pokemonRepository.findByUser(user, pageable);
        return pokemonsPage.map(pokemonMapper::toResponse);
    }
    
    public Page<UserPokemonResponse> getAllPokemons(Pageable pageable) {
        if (!authService.isAdmin(authService.getAuthenticatedUser())) {
            throw new SecurityException("Unauthorized to access all pokemons.");
        }

        Page<Pokemon> pokemonPage = pokemonRepository.findAll(pageable);
        return pokemonPage.map(pokemon -> new UserPokemonResponse(pokemon.getUser().getId(), pokemon.getUser().getUsername(), List.of(pokemonMapper.toResponse(pokemon))));
    }
    
}
