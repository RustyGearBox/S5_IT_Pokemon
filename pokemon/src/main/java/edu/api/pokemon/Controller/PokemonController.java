package edu.api.pokemon.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.api.pokemon.Model.Request.PokemonActionRequest;
import edu.api.pokemon.Model.Request.PokemonFindRequest;
import edu.api.pokemon.Model.Request.PokemonRequest;
import edu.api.pokemon.Model.Response.PokemonResponse;
import edu.api.pokemon.Service.PokemonService;
import edu.api.pokemon.Service.UpdateService;
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
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@RestController
@RequestMapping("/pokemon")
@RequiredArgsConstructor
public class PokemonController {

    private final PokemonService pokemonService;
    private final UpdateService updateService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PokemonResponse> createPokemon(@RequestBody PokemonRequest pokemonRequest) {
        PokemonResponse pokemonResponse = pokemonService.createPokemon(pokemonRequest);
        return new ResponseEntity<>(pokemonResponse, HttpStatus.CREATED);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deletePokemon(@RequestBody PokemonFindRequest findRequest, Principal principal) {
        pokemonService.deletePokemon(findRequest, principal.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PokemonResponse> getPokemon(@PathVariable int id) {
        PokemonResponse pokemonResponse = pokemonService.getPokemon(id);
        return new ResponseEntity<>(pokemonResponse, HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Page<PokemonResponse>> getPokemons(
        @PageableDefault(page = 0, size = 5, sort = "name", direction = Sort.Direction.ASC) Pageable pageable,
        Principal principal) {

        Page<PokemonResponse> pokemons = pokemonService.getPokemons(pageable, principal.getName());
        return ResponseEntity.ok(pokemons);
    }

    @PostMapping("/action")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PokemonResponse> updatePokemon(@RequestBody PokemonActionRequest pokemonActionRequest) {
        PokemonResponse pokemonResponse = updateService.updatePokemon(pokemonActionRequest);
        return new ResponseEntity<>(pokemonResponse, HttpStatus.OK);
    }
}
