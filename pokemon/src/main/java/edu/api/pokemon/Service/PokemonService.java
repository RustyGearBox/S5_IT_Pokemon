package edu.api.pokemon.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edu.api.pokemon.Enums.PokemonRooms;
import edu.api.pokemon.Enums.Role;
import edu.api.pokemon.Exception.Custom.PokemonNameExistException;
import edu.api.pokemon.Exception.Custom.PokemonNotFoundException;
import edu.api.pokemon.Model.Pokemon;
import edu.api.pokemon.Model.User;
import edu.api.pokemon.Model.Request.PokemonFindRequest;
import edu.api.pokemon.Model.Request.PokemonRequest;
import edu.api.pokemon.Model.Response.PokemonResponse;
import edu.api.pokemon.Model.Response.UserPokemonResponse;
import edu.api.pokemon.Repository.PokemonRepository;
import edu.api.pokemon.Repository.UserRepository;
import edu.api.pokemon.Service.Interface.IPokemonService;
import lombok.RequiredArgsConstructor;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PokemonService implements IPokemonService {
    
    private final AuthService authService;
    private final PokemonMapper pokemonMapper;
    private final PokemonRepository pokemonRepository;
    private final UserRepository userRepository;

    public PokemonResponse createPokemon(PokemonRequest request) {
        
        User user = authService.getAuthenticatedUser();

        if (user.hasPokemonsWithNickname(request.getNickname())) {
            throw new PokemonNameExistException(request.getNickname());
        }

        Pokemon pokemon = pokemonMapper.toEntity(request, user, PokemonRooms.BEDROOM);
        Pokemon savedPokemon = pokemonRepository.save(pokemon);

        return pokemonMapper.toResponse(savedPokemon);
    }
    
    public Pokemon getPokemonById(int id) {
        return pokemonRepository.findById(id)
            .orElseThrow(() -> new PokemonNotFoundException(id));
    }
    
    public void deletePokemon(PokemonFindRequest findRequest, String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Pokemon> pokemonOptional = pokemonRepository.findById(findRequest.getId());

        if (pokemonOptional.isPresent()) {
            Pokemon pokemon = pokemonOptional.get();

            // Admins can delete any Pokémon, Users can only delete their own
            if (user.getRole() == Role.ADMIN || pokemon.getUser().equals(user)) {
                pokemonRepository.delete(pokemon);
            } else {
                throw new RuntimeException("You are not authorized to delete this Pokémon.");
            }
        } else {
            throw new RuntimeException("Pokemon not found.");
        }
    }
    
    public PokemonResponse getPokemon (int id) {
        Pokemon pokemon = getPokemonById(id);
        pokemon.verifyAdminOrOwner(authService.getAuthenticatedUser(), authService);
        return pokemonMapper.toResponse(pokemon);
    }

    public Page<PokemonResponse> getPokemons(Pageable pageable, String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Page<Pokemon> pokemonPage;

        // Admins see all Pokémon, users see only their own
        if (user.getRole() == Role.ADMIN) {
            pokemonPage = pokemonRepository.findAll(pageable);
        } else {
            pokemonPage = pokemonRepository.findByUser(user, pageable);
        }

        return pokemonPage.map(pokemonMapper::toResponse); // Using PokemonMapper
    }
    
    public Page<UserPokemonResponse> getAllPokemons(Pageable pageable) {
        if (!authService.isAdmin(authService.getAuthenticatedUser())) {
            throw new SecurityException("Unauthorized to access all pokemons.");
        }

        Page<Pokemon> pokemonPage = pokemonRepository.findAll(pageable);
        return pokemonPage.map(pokemon -> new UserPokemonResponse(pokemon.getUser().getId(), pokemon.getUser().getUsername(), List.of(pokemonMapper.toResponse(pokemon))));
    }
    
}
