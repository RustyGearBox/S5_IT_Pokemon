package edu.api.pokemon.Model.Response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPokemonResponse {
    
    private int userId;
    private String username;
    private List<PokemonResponse> pokemons;

}
