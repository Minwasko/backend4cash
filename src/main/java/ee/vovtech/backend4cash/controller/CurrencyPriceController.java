package ee.vovtech.backend4cash.controller;


import com.mashape.unirest.http.exceptions.UnirestException;
import ee.vovtech.backend4cash.model.Currency;
import ee.vovtech.backend4cash.model.TimestampPrice;
import ee.vovtech.backend4cash.service.coingecko.CoingeckoAPI;
import ee.vovtech.backend4cash.service.currency.CurrencyPriceService;
import ee.vovtech.backend4cash.service.currency.CurrencyService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:4003", "http://localhost:4200"}, maxAge = 3600)
public class CurrencyPriceController {

    @Autowired
    private CurrencyPriceService currencyPriceService;
    @Autowired
    private CurrencyService currencyService;


    @GetMapping("/coins/{id}/price_past_day")
    public List<TimestampPrice> getPricePastDay(@PathVariable String id){
        return currencyPriceService.getPriceBetween(id, Instant.now().getEpochSecond() - (60 * 60 * 24 - 10), Instant.now().getEpochSecond());
    }

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
