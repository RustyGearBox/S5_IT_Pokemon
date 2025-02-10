package edu.api.pokemon.Exception.Custom;

public class SleepingPokemonException extends RuntimeException {
    public SleepingPokemonException(String name) {
        super("Pokemon " + name + " is sleeping");
    }
}
