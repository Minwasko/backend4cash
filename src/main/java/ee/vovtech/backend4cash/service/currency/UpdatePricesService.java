package ee.vovtech.backend4cash.service.currency;

import ee.vovtech.backend4cash.repository.CurrencyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UpdatePricesService {

    private final CurrencyRepository currencyRepository;
    private final UpdatePricesConfig updatePricesConfig;

    @Scheduled(cron = "${coins.cron}", zone = "Europe/Tallinn")
    public void updatePrices() {
        if (updatePricesConfig.notUpdating()) {
            System.out.println("not doing it");
        }
            System.out.println("doing it");
        // TODO
        // new schedule each few hours +100 coins to each user
            // loop coins
            // for each get last hour data
    }



}
