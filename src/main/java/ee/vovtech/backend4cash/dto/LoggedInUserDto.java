package ee.vovtech.backend4cash.dto;

import ee.vovtech.backend4cash.security.DbRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.AbstractMap;
import java.util.List;

@Getter @Setter @Builder
public class LoggedInUserDto {

    private long id;
    private String username;
    private String email;
    private String cash;
    private String status;
    @Enumerated(EnumType.STRING)
    private DbRole role;
    @ElementCollection
    private List<AbstractMap.SimpleEntry<String, String>> ownedCoins;
}
