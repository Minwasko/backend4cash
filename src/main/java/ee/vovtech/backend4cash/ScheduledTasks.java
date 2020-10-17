package ee.vovtech.backend4cash;

import ee.vovtech.backend4cash.model.Currency;
import ee.vovtech.backend4cash.service.currency.CurrencyPriceService;
import ee.vovtech.backend4cash.service.currency.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Deprecated
@Component
public class ScheduledTasks {

    @Autowired
    CurrencyPriceService currencyPriceService;
    @Autowired
    CurrencyService currencyService;

    @Scheduled(fixedDelay = 1000)
    public void updatePrices(){
        currencyService.findAll().forEach(currency -> currencyPriceService.updatePrice(currency.getName(), CurrencyPriceService.UpdateTime.HOUR));
    }

}