package ee.vovtech.backend4cash.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import ee.vovtech.backend4cash.model.Currency;
import ee.vovtech.backend4cash.service.currency.CurrencyPriceService;
import ee.vovtech.backend4cash.service.currency.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = {"http://bits4cash.tk", "http://www.bits4cash.tk"}, maxAge = 3600)
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
    public Currency getCurrency(@PathVariable String id) throws UnirestException {
        return currencyService.findById(id);
    }

    @PostMapping
    public Currency saveCurrency(@RequestBody Currency currency) {
        return currencyService.save(currency);
    }

    @PutMapping("{id}")
    public Currency updateCurrency(@PathVariable String id, @RequestBody Currency currency) throws UnirestException {
        return currencyService.updateCurrency(id, currency);
    }

    @DeleteMapping("{id}")
    public void deleteCurrency(@PathVariable String id) throws UnirestException {
        currencyService.delete(id);
    }

    @PutMapping("{coinId}/buy")
    public boolean buyCoins(@PathVariable("coinId") String coinId, @RequestParam String amount, @RequestParam Long userId) throws UnirestException {
        return currencyPriceService.tryToBuyCoins(userId, coinId, amount);
    }

    @PutMapping("{coinId}/sell")
    public boolean sellCoins(@PathVariable("coinId") String coinId, @RequestParam String amount, @RequestParam Long userId) throws UnirestException {
        return currencyPriceService.tryToSellCoins(userId, coinId, amount);
    }
}
