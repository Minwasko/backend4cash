package ee.vovtech.backend4cash.service.currency;

import com.mashape.unirest.http.exceptions.UnirestException;
import ee.vovtech.backend4cash.model.Currency;
import ee.vovtech.backend4cash.service.currency.CurrencyPriceService.UpdateTime;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UpdatePricesService {

    private final CurrencyService currencyService;
    private final CurrencyPriceService currencyPriceService;
    private final UpdatePricesConfig updatePricesConfig;

    @Scheduled(cron = "${coins.cron}", zone = "Europe/Tallinn")
    public void updatePrices() throws UnirestException {
        if (updatePricesConfig.notUpdating()) {
            log.warn("Currency auto update turned off in the config");
        }
        List<Currency> currencies = currencyService.findAll();
        for(Currency currency : currencies){
            currencyPriceService.updatePrice(currency.getName(), UpdateTime.HOUR);
            log.info("Successfully updated " + currency.getName());
        }
            log.info("Successfully updated all planned currencies.");
    }



}
