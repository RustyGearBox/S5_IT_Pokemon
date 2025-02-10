package edu.api.pokemon.Exception.Custom;

public class PokemonNotFoundException extends RuntimeException {
    public PokemonNotFoundException(String name) {
        super("Pokemon " + name + " not found");
    }
}
