package edu.api.pokemon.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.api.pokemon.Model.Request.PokemonFindRequest;
import edu.api.pokemon.Model.Request.PokemonRequest;
import edu.api.pokemon.Model.Response.PokemonResponse;
import edu.api.pokemon.Service.Interface.IPokemonService;

public class PokemonService implements IPokemonService {
    
    public PokemonResponse createPokemon(PokemonRequest request) {
        return null;
    }
    
    public PokemonResponse getPokemonById(Long id) {
        return null;
    }
    
    public void deletePokemon(PokemonFindRequest pokemonFindRequest) {
        
    }
    
    public Page<PokemonResponse> getUserPokemons(Pageable pageable) {
        return null;
    }
    
    public Page<PokemonResponse> getAllPokemons(Pageable pageable) {
        return null;
    }
    
}
