package edu.api.pokemon.Service.Interface;

import edu.api.pokemon.Model.Pokemon;
import edu.api.pokemon.Model.Request.PokemonActionRequest;
import edu.api.pokemon.Model.Response.PokemonResponse;

public interface IUpdateService {
    PokemonResponse updatePokemon(PokemonActionRequest pokemonActionRequest);
    Pokemon verifyOwner(PokemonActionRequest pokemonActionRequest);
}
