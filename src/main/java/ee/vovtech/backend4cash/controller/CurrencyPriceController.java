package ee.vovtech.backend4cash.controller;


import ee.vovtech.backend4cash.model.Currency;
import ee.vovtech.backend4cash.model.CurrencyPrice;
import ee.vovtech.backend4cash.service.currency.CurrencyPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "localhost:4200")
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
