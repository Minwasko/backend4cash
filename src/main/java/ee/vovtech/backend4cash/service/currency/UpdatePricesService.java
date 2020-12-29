package ee.vovtech.backend4cash.service.currency;

import ee.vovtech.backend4cash.service.currency.CurrencyPriceService.UpdateTime;
import ee.vovtech.backend4cash.model.Currency;
import lombok.AllArgsConstructor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;

import com.mashape.unirest.http.exceptions.UnirestException;

@Service
@AllArgsConstructor
public class UpdatePricesService {

    private final CurrencyService currencyService;
    private final CurrencyPriceService currencyPriceService;
    private final UpdatePricesConfig updatePricesConfig;

    @Scheduled(cron = "${coins.cron}", zone = "Europe/Tallinn")
    public void updatePrices() throws UnirestException {
        if (updatePricesConfig.notUpdating()) {
            System.out.println("not doing it");
        }
        List<Currency> currencies = currencyService.findAll();
        for(Currency currency : currencies){
            currencyPriceService.updatePrice(currency.getName(), UpdateTime.HOUR);
            System.out.println("Updated " + currency.getName());
        }
            System.out.println("doing it");
    }



}
