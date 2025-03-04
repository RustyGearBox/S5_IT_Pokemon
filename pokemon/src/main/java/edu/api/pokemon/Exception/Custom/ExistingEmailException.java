package edu.api.pokemon.Exception.Custom;

public class ExistingEmailException extends RuntimeException {
    public ExistingEmailException(String email) {
        super("Email " + email + " already exists");
    }
}
