package edu.api.pokemon.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.api.pokemon.Enums.PokemonActions;
import edu.api.pokemon.Model.Request.PokemonFindRequest;
import edu.api.pokemon.Model.Request.PokemonRequest;
import edu.api.pokemon.Model.Response.PokemonResponse;
import edu.api.pokemon.Model.Response.UserPokemonResponse;
import edu.api.pokemon.Service.PokemonService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/pokemon")
@RequiredArgsConstructor
public class PokemonController {
    
    private final PokemonService pokemonService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PokemonResponse> createPokemon(@RequestBody PokemonRequest pokemonRequest) {
        PokemonResponse pokemonResponse = pokemonService.createPokemon(pokemonRequest);
        return new ResponseEntity<>(pokemonResponse, HttpStatus.CREATED);
    }
    
    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deletePokemon(@RequestBody PokemonFindRequest findRequest) {
        pokemonService.deletePokemon(findRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PokemonResponse> getPokemon(@RequestParam int id) {
        PokemonResponse pokemonResponse = pokemonService.getPokemon(id);
        return new ResponseEntity<>(pokemonResponse, HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<PokemonResponse>> getUserPokemons(@PageableDefault(page = 0, size = 5, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(pokemonService.getUserPokemons(pageable));
    }

    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserPokemonResponse>> getAllPokemons(@PageableDefault(page = 0, size = 5, sort = "user", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(pokemonService.getAllPokemons(pageable));
    }

    @PostMapping("/action")
    public ResponseEntity<PokemonResponse> updatePokemon(@RequestBody PokemonRequest pokemonRequest, @RequestParam PokemonActions action) {
        PokemonResponse pokemonResponse = updateService.actionPokemon(pokemonRequest, action);
        return new ResponseEntity<>(pokemonResponse, HttpStatus.OK);
    }
    

}