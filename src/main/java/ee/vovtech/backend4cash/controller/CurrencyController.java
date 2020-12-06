package ee.vovtech.backend4cash.controller;

import ee.vovtech.backend4cash.model.Currency;
import ee.vovtech.backend4cash.service.currency.CurrencyPriceService;
import ee.vovtech.backend4cash.service.currency.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = {"http://localhost:4003", "http://localhost:4200"}, maxAge = 3600)
@RestController
@RequestMapping("/coins")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    CurrencyPriceService currencyPriceService;

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

    @PutMapping("{coinId}/buy")
    public boolean buyCoins(@PathVariable("coinId") String coinId, @RequestParam String amount, @RequestParam Long userId) {
        return currencyPriceService.tryToBuyCoins(userId, coinId, amount);
    }

    @PutMapping("{coinId}/sell")
    public boolean sellCoins(@PathVariable("coinId") String coinId, @RequestParam String amount, @RequestParam Long userId) {
        return currencyPriceService.tryToSellCoins(userId, coinId, amount);
    }
}
