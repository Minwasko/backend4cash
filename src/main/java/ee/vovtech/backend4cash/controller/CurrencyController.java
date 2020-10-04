package ee.vovtech.backend4cash.controller;

import ee.vovtech.backend4cash.model.Currency;
import ee.vovtech.backend4cash.model.CurrencyPrice;
import ee.vovtech.backend4cash.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("coins")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping
    public List<Currency> getCurrencies() {
       return currencyService.findAll();
    }

    @GetMapping("{id}")
    public Currency getCurrency(@PathVariable String id) {
        return currencyService.findById(id);
    }

    @PostMapping
    public Currency saveCurrency(@RequestBody Currency currency) {
        return currencyService.save(currency);
    }

    @PutMapping("{id}")
    public Currency updateCurrencyPrice(@RequestBody CurrencyPrice price, @PathVariable String id) {
        return currencyService.updatePrice(id, price);
    }

    @DeleteMapping("{id}")
    public void deleteCurrency(@PathVariable String id) {
        currencyService.delete(id);
    }
}
