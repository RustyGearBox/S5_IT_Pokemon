package edu.api.pokemon.Service.Interface;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.api.pokemon.Model.Pokemon;
import edu.api.pokemon.Model.Request.PokemonFindRequest;
import edu.api.pokemon.Model.Request.PokemonRequest;
import edu.api.pokemon.Model.Response.PokemonResponse;
import edu.api.pokemon.Model.Response.UserPokemonResponse;

public interface IPokemonService {
    
    PokemonResponse createPokemon(PokemonRequest request);
    Pokemon getPokemonById(int id);
    void deletePokemon(PokemonFindRequest request, String username);
    Page<UserPokemonResponse> getAllPokemons(Pageable pageable);
    
}
