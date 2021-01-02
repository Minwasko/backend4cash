package ee.vovtech.backend4cash.dto;

import ee.vovtech.backend4cash.security.DbRole;
import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;
import java.util.Map;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class LoggedInUserDto {

    private long id;
    private String username;
    private String email;
    private String cash;
    private String status;
    @Enumerated(EnumType.STRING)
    private DbRole role;
    @ElementCollection
    private List<Map.Entry<String, String>> ownedCoins;

}
