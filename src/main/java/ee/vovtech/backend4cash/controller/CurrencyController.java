package ee.vovtech.backend4cash.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.mysql.cj.xdevapi.JsonArray;
import ee.vovtech.backend4cash.model.Currency;
import ee.vovtech.backend4cash.service.coingecko.CoingeckoAPI;
import ee.vovtech.backend4cash.service.currency.CurrencyService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4003", maxAge = 3600)
@RestController
@RequestMapping("/coins")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/gleb")
    public String getData() throws UnirestException {
        return CoingeckoAPI.getPriceData("bitcoin").toString() + "\n" + Instant.ofEpochSecond(Instant.now().getEpochSecond() - 6000);
//        return currencyService.updateCoinsData().toString();
    }

    @GetMapping("get_prices/{id}")
    public String getPrices(@PathVariable String id) throws UnirestException {
        return CoingeckoAPI.getPriceData(id).toString();
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
