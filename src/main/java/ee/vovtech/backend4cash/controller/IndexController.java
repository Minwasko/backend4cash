package ee.vovtech.backend4cash.controller;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController("/")
public class IndexController {

    @GetMapping()
    public String index() {
        return "Hello there";
    }
    @RequestMapping(value = "coins")
    public String hello() throws UnirestException {
        return bitcoin();
    }

    public String bitcoin() throws UnirestException {
        HttpResponse<JsonNode> httpResponse = Unirest.get("https://api.coingecko.com/api/v3/coins/bitcoin")
                .queryString("localization", "false")
                .queryString("tickers", "false")
                .queryString("market_data", "false")
                .asJson();

        return httpResponse.getBody().getObject().getJSONObject("description").get("en").toString();
    }
}
