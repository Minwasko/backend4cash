package ee.vovtech.backend4cash.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class RegisterDto {

    private String username;
    private String email;
    private String password;
}
