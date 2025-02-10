package edu.api.pokemon.Exception.Custom;

public class PokemonNameExistException extends RuntimeException {
    public PokemonNameExistException(String name) {
        super("Pokemon " + name + " already exists");
    }
}
