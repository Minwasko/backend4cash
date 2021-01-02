package ee.vovtech.backend4cash.controller;


import com.mashape.unirest.http.exceptions.UnirestException;
import ee.vovtech.backend4cash.model.TimestampPrice;
import ee.vovtech.backend4cash.service.currency.CurrencyPriceService;
import ee.vovtech.backend4cash.service.currency.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/coins")
public class CurrencyPriceController {

    @Autowired
    private CurrencyPriceService currencyPriceService;
    @Autowired
    private CurrencyService currencyService;

    @GetMapping("{id}/pricedata")
    public List<TimestampPrice> getCurrencyPriceData(@PathVariable String id) throws UnirestException {
        return currencyService.findById(id.replace(" ", "-")).getTimestampPrices();
    }

    @GetMapping("{id}/price")
    public String getCurrentPrice(@PathVariable String id) throws UnirestException {
        return currencyPriceService.getCurrentPrice(id.replace(" ", "-"));
    }

}
