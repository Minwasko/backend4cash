package ee.vovtech.backend4cash.controller;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import ee.vovtech.backend4cash.model.Currency;
import ee.vovtech.backend4cash.repository.CurrencyRepository;
import ee.vovtech.backend4cash.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController("/")
public class IndexController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("{id}")
    public Currency getCurrency(@PathVariable String id) {
        return currencyService.findById(id);
    }

    @GetMapping()
    public String index() {
        return "Hello there";
    }


    @GetMapping(value = "coins")
    public String hello() throws UnirestException {
        return bitcoin();
    }
//
//    @GetMapping("{id}")
//    public Currency getCurrency(@PathVariable Long id) {
//        return heroesService.findById(id);
//    }

    public String bitcoin() throws UnirestException {
        HttpResponse<JsonNode> httpResponse = Unirest.get("https://api.coingecko.com/api/v3/coins/bitcoin")
                .queryString("localization", "false")
                .queryString("tickers", "false")
                .queryString("market_data", "false")
                .asJson();

        return httpResponse.getBody().getObject().getJSONObject("description").get("en").toString();
    }
}
