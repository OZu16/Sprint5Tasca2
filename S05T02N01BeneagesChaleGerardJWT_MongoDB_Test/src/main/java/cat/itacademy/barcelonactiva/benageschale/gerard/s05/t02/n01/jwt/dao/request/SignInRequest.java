package cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.dao.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {

    private String email;

    private String password;
}