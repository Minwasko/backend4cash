package ee.vovtech.backend4cash.dto;

import ee.vovtech.backend4cash.security.DbRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class LoginResponse {

    private long id;
    private String email;
    private String token;
    private DbRole role;
    private boolean success;
}
