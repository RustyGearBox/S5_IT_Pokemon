package edu.api.pokemon.Model.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PokemonResponse {
    
    private int id;
    private String name;
    private String nickname;
    private int experience;
    private int happiness;
    private int health;
    private String room;

}
