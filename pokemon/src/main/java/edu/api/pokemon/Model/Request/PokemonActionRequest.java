package edu.api.pokemon.Model.Request;

import edu.api.pokemon.Enums.PokemonActions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PokemonActionRequest {
    
    private String name;
    private String nickname;
    private PokemonActions action;
    private int pokemonId;
    private int userId;

}
