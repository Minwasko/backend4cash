package ee.vovtech.backend4cash.controller;

import ee.vovtech.backend4cash.security.Roles;
import com.mashape.unirest.http.exceptions.UnirestException;
import ee.vovtech.backend4cash.model.Currency;
import ee.vovtech.backend4cash.service.currency.CurrencyPriceService;
import ee.vovtech.backend4cash.service.currency.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//@CrossOrigin(origins = {"https://bits4cash.tk", "https://www.bits4cash.tk", "https://frontend4cashdev"}, maxAge = 3600)
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

    @Secured(Roles.ADMIN)
    @PostMapping
    public Currency saveCurrency(@RequestBody Currency currency) {
        return currencyService.save(currency);
    }

    @Secured(Roles.ADMIN)
    @PutMapping("{id}")
    public Currency updateCurrency(@PathVariable String id, @RequestBody Currency currency) throws UnirestException {
        return currencyService.updateCurrency(id, currency);
    }

    @Secured(Roles.ADMIN)
    @DeleteMapping("{id}")
    public void deleteCurrency(@PathVariable String id) throws UnirestException {
        currencyService.delete(id);
    }

    @Secured({Roles.USER, Roles.ADMIN})
    @PutMapping("{coinId}/buy")
    public boolean buyCoins(@PathVariable("coinId") String coinId, @RequestParam String amount, @RequestParam Long userId) throws UnirestException {
        return currencyPriceService.tryToBuyCoins(userId, coinId, amount);
    }

    @Secured({Roles.USER, Roles.ADMIN})
    @PutMapping("{coinId}/sell")
    public boolean sellCoins(@PathVariable("coinId") String coinId, @RequestParam String amount, @RequestParam Long userId) throws UnirestException {
        return currencyPriceService.tryToSellCoins(userId, coinId, amount);
    }
}
