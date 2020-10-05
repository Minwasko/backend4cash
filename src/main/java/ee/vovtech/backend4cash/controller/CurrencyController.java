package ee.vovtech.backend4cash.controller;

import ee.vovtech.backend4cash.model.Currency;
import ee.vovtech.backend4cash.service.currency.CoingeckoAPI;
import ee.vovtech.backend4cash.service.currency.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/coins")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/gleb")
    public String getData() {
        return CoingeckoAPI.updateCurrencies();
    }

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
    public Currency updateCurrency(@PathVariable String id, @RequestBody Currency currency) {
        return currencyService.updateCurrency(id, currency);
    }

    @DeleteMapping("{id}")
    public void deleteCurrency(@PathVariable String id) {
        currencyService.delete(id);
    }
}
