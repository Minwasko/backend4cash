package ee.vovtech.backend4cash.controller;


import ee.vovtech.backend4cash.model.Currency;
import ee.vovtech.backend4cash.model.CurrencyPrice;
import ee.vovtech.backend4cash.service.currency.CurrencyPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyPriceController {

    @Autowired
    CurrencyPriceService currencyPriceService;


    @GetMapping("/coins/{id}/pricedata")
    public CurrencyPrice getCurrencyPriceData(@PathVariable String id) {
        return currencyPriceService.findById(id);
    }

    @PutMapping("/coins/{id}/pricedata")
    public Currency updateCurrencyPriceData(@PathVariable String id, @RequestBody CurrencyPrice currencyPrice) {
        return currencyPriceService.update(id, currencyPrice);
    }
}
