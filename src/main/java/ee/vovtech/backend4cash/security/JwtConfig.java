package ee.vovtech.backend4cash.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    private String secret;
    private int durationMinutes;

    public int getDurationMillis() {
        return durationMinutes * 60 * 1000;
    }
}
