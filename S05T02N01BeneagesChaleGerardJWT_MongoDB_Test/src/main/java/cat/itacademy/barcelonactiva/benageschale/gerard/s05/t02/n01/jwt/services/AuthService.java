package cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.services;


import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.dao.request.SignInRequest;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.dao.request.SignUpRequest;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.dao.response.JwtAuthenticationResponse;

public interface AuthService {

    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SignInRequest request);
}