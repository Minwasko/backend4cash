package ee.vovtech.backend4cash.service.currency;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "coins")
public class UpdatePricesConfig {

    private boolean updating;
    private String cron;

    public boolean notUpdating() {
        return !updating;
    }
}
