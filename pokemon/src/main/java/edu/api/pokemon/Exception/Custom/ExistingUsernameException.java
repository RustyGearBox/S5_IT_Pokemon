package edu.api.pokemon.Exception.Custom;

public class ExistingUsernameException extends RuntimeException {
    public ExistingUsernameException(String username) {
        super("Username " + username + " already exists");
    }
}
