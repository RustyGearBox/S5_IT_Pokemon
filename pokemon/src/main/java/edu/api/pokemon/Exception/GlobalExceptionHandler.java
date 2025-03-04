package edu.api.pokemon.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import edu.api.pokemon.Exception.Custom.ExistingEmailException;
import edu.api.pokemon.Exception.Custom.ExistingUsernameException;
import edu.api.pokemon.Exception.Custom.PokemonNameExistException;
import edu.api.pokemon.Exception.Custom.PokemonNotFoundException;
import edu.api.pokemon.Exception.Custom.SleepingPokemonException;
import edu.api.pokemon.Exception.Custom.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }

    @ExceptionHandler(ExistingUsernameException.class)
    public ResponseEntity<String> handleExistingUsernameException(ExistingUsernameException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(ExistingEmailException.class)
    public ResponseEntity<String> handleExistingEmailException(ExistingEmailException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(PokemonNameExistException.class)
    public ResponseEntity<String> handlePokemonNameExistException(PokemonNameExistException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(PokemonNotFoundException.class)
    public ResponseEntity<String> handlePokemonNotFoundException(PokemonNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(SleepingPokemonException.class)
    public ResponseEntity<String> handleSleepingPokemonException(SleepingPokemonException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

}
