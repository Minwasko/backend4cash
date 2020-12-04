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
@RequestMapping({"/coins", "/coins2"})
@CrossOrigin(origins = {"http://localhost:4003", "http://localhost:4200"}, maxAge = 3600)
public class CurrencyPriceController {

    @Autowired
    private CurrencyPriceService currencyPriceService;
    @Autowired
    private CurrencyService currencyService;

    @GetMapping("{id}/pricedata")
    public List<TimestampPrice> getCurrencyPriceData(@PathVariable String id) {
        return currencyService.findById(id).getTimestampPrices();
    }

    @PutMapping("{id}/pricedata")
    public Currency updateCurrencyPriceData(@PathVariable String id, @RequestBody List<TimestampPrice> timestampPrices) {
        return currencyPriceService.updateDB(id, timestampPrices);
    }

}
