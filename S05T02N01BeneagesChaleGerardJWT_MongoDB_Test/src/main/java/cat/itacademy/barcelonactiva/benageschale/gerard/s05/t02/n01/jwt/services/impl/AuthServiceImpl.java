package cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.services.impl;


import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.dao.request.SignInRequest;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.dao.request.SignUpRequest;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.dao.response.JwtAuthenticationResponse;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.domain.User;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.exceptions.UserAlreadyExistException;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.repository.UserRepository;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.enums.Role;


import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.services.AuthService;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        if (request.getEmail().isEmpty() || request.getPassword().isEmpty()) {
            throw new IllegalArgumentException("User email and password cannot be null");
        }
        userRepository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new UserAlreadyExistException("Email is already registered:" + user.getEmail());
                });
        User user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).build();
        userRepository.save(user);
        String jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + request.getEmail()));
        String jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}