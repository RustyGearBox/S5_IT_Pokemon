package edu.api.pokemon.Service.Interface;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.api.pokemon.Model.Request.PokemonFindRequest;
import edu.api.pokemon.Model.Request.PokemonRequest;
import edu.api.pokemon.Model.Response.PokemonResponse;

public interface IPokemonService {
    
    PokemonResponse createPokemon(PokemonRequest request);
    PokemonResponse getPokemonById(Long id);
    void deletePokemon(PokemonFindRequest pokemonFindRequest);
    Page<PokemonResponse> getUserPokemons(Pageable pageable);
    Page<PokemonResponse> getAllPokemons(Pageable pageable);
    
}
