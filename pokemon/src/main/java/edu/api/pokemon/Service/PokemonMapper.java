package edu.api.pokemon.Service;

import org.springframework.stereotype.Component;

import edu.api.pokemon.Enums.PokemonRooms;
import edu.api.pokemon.Model.Pokemon;
import edu.api.pokemon.Model.User;
import edu.api.pokemon.Model.Request.PokemonRequest;
import edu.api.pokemon.Model.Response.PokemonResponse;

@Component
public class PokemonMapper {
    
    public Pokemon toEntity(PokemonRequest request, User user, PokemonRooms rooms) {
        return Pokemon.builder()
            .user(user)
            .name(request.getName())
            .nickname(request.getNickname())
            .health(100)
            .happiness(50)
            .experience(0)
            .room(rooms)
            .build();
    }

    public PokemonResponse toResponse(Pokemon pokemon) {
        return PokemonResponse.builder()
            .id(pokemon.getId())
            .name(pokemon.getName())
            .nickname(pokemon.getNickname())
            .health(pokemon.getHealth())
            .happiness(pokemon.getHappiness())
            .experience(pokemon.getExperience())
            .room(pokemon.getRoom())
            .build();
    }

}
