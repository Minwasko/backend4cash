package ee.vovtech.backend4cash.controller;


import com.mashape.unirest.http.exceptions.UnirestException;
import ee.vovtech.backend4cash.model.Currency;
import ee.vovtech.backend4cash.model.TimestampPrice;
import ee.vovtech.backend4cash.service.coingecko.CoingeckoAPI;
import ee.vovtech.backend4cash.service.currency.CurrencyPriceService;
import ee.vovtech.backend4cash.service.currency.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "localhost:4200")
public class CurrencyPriceController {

    @Autowired
    private CurrencyPriceService currencyPriceService;
    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/coins/{id}/price_between")
    public String getCurrencyPriceBetween(@PathVariable String id, @RequestParam long from, @RequestParam long to) {
        return currencyPriceService.getPriceBetween(id, from , to).toString();
    }


    @GetMapping("/coins/{id}/all_time_pricedata")
    public String getPrices(@PathVariable String id) throws UnirestException {
        return CoingeckoAPI.getPriceData(id).toString();
    }


    @GetMapping("/coins/{id}/pricedata")
    public List<TimestampPrice> getCurrencyPriceData(@PathVariable String id) {
        return currencyService.findById(id).getTimestampPrices();
    }

    @PutMapping("/coins/{id}/pricedata")
    public Currency updateCurrencyPriceData(@PathVariable String id, @RequestBody List<TimestampPrice> timestampPrices) {
        return currencyPriceService.update(id, timestampPrices);
    }
}
