package edu.api.pokemon.Service.Interface;

import edu.api.pokemon.Enums.PokemonActions;
import edu.api.pokemon.Model.Pokemon;
import edu.api.pokemon.Model.Request.PokemonRequest;
import edu.api.pokemon.Model.Response.PokemonResponse;

public interface IUpdateService {
    PokemonResponse updatePokemon(PokemonRequest pokemonRequest, PokemonActions action);
    Pokemon verifyOwner(int id);
}
