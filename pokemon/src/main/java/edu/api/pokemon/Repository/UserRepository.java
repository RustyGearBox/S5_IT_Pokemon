package edu.api.pokemon.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.api.pokemon.Model.User;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username); 
}
