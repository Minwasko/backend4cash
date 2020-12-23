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
@RequestMapping("/coins")
@CrossOrigin(origins = {"http://bits4cash.tk", "http://www.bits4cash.tk"}, maxAge = 3600)
public class CurrencyPriceController {

    @Autowired
    private CurrencyPriceService currencyPriceService;
    @Autowired
    private CurrencyService currencyService;

    @GetMapping("{id}/pricedata")
    public List<TimestampPrice> getCurrencyPriceData(@PathVariable String id) throws UnirestException {
        return currencyService.findById(id).getTimestampPrices();
    }

    @PutMapping("{id}/pricedata")
    public Currency updateCurrencyPriceData(@PathVariable String id, @RequestBody List<TimestampPrice> timestampPrices) {
        return currencyPriceService.updateDB(id, timestampPrices);
    }

    @GetMapping("{id}/price")
    public String getCurrentPrice(@PathVariable String id) throws UnirestException {
        return currencyPriceService.getCurrentPrice(id);
    }

}
