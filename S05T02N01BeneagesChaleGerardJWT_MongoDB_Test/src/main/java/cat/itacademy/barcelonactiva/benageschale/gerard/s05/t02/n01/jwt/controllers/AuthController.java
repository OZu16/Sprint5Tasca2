package cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.controllers;

import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.dao.request.SignInRequest;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.dao.request.SignUpRequest;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.dao.response.JwtAuthenticationResponse;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.services.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }
}
