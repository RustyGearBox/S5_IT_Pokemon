package edu.api.pokemon.Service.Interface;

import edu.api.pokemon.Model.User;
import edu.api.pokemon.Model.Request.LoginRequest;
import edu.api.pokemon.Model.Request.RegisterRequest;
import edu.api.pokemon.Model.Response.AuthResponse;

public interface IAuthService {
    
    AuthResponse login(LoginRequest request);
    AuthResponse register(RegisterRequest request);
    User getAuthenticatedUser();
    boolean isAdmin(User user);
    
}
