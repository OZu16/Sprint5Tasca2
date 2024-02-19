package cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUserName(String token);

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);
}