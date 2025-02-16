package edu.api.pokemon.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.api.pokemon.Model.Pokemon;
import edu.api.pokemon.Model.User;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon,Integer> {
    Page<Pokemon> findByUser(User user, Pageable pageable);
    Page<Pokemon> findAll(Pageable pageable);
}
