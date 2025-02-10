package edu.api.pokemon.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.api.pokemon.Model.Pokemon;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon,Integer> {
    Optional<Pokemon> findByUser(String name, Pageable pageable);
    Page<Pokemon> findAll(Pageable pageable);
}
