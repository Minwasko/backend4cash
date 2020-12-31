package ee.vovtech.backend4cash.controller;

import ee.vovtech.backend4cash.RestTemplateTests;
import ee.vovtech.backend4cash.model.Currency;
import ee.vovtech.backend4cash.model.TimestampPrice;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.yaml")
class CurrencyPriceControllerTest extends RestTemplateTests {

    public static final ParameterizedTypeReference<List<Currency>> LIST_OF_CURRENCIES =
            new ParameterizedTypeReference<>() {};
    public static final ParameterizedTypeReference<List<TimestampPrice>> LIST_OF_PRICES =
            new ParameterizedTypeReference<>() {};

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void getPriceDataContainsData(){
        ResponseEntity<List<TimestampPrice>> exchange = testRestTemplate.exchange("/coins/bitcoin/pricedata",
                HttpMethod.GET, null, LIST_OF_PRICES);
        List<TimestampPrice> prices = assertOk(exchange);
        assertNotNull(prices);
        assertTrue(Float.parseFloat(prices.get(0).getPrice()) > 10); // idk how low it could drop, but bitcoin will probably remain over 10$ for a while
    }

    @Test
    void coinNotInDbGetsParsedInstantly() {
        // ensure dogecoin is not in db
        ResponseEntity<List<Currency>> exchange = testRestTemplate.exchange("/coins", HttpMethod.GET, null, LIST_OF_CURRENCIES);
        List<Currency> currencies = assertOk(exchange);
        assertFalse(currencies.stream().map(Currency::getName).collect(Collectors.toList()).contains("dogecoin"));
        // get price for dogecoin will parse and add it with the prices
        ResponseEntity<List<TimestampPrice>> exchangeDoge = testRestTemplate.exchange("/coins/dogecoin/pricedata",
                HttpMethod.GET, null, LIST_OF_PRICES);
        List<TimestampPrice> prices = assertOk(exchangeDoge);
        assertTrue(Float.parseFloat(prices.get(0).getPrice()) < 10); // dogecoin is cheap, most probably never will be >10$
        // check that it is added to db
        ResponseEntity<List<Currency>> exchangeWithDoge = testRestTemplate.exchange("/coins", HttpMethod.GET, null, LIST_OF_CURRENCIES);
        List<Currency> currenciesWithDoge = assertOk(exchangeWithDoge);
        assertTrue(currenciesWithDoge.stream().map(Currency::getName).collect(Collectors.toList()).contains("dogecoin"));
    }

    @Test
    void getCurrentPriceReturnsPrice() {
        // getting price
        ResponseEntity<String> exchange = testRestTemplate.exchange("/coins/bitcoin/price", HttpMethod.GET, null, String.class);
        String price = assertOk(exchange);
        assertTrue(Float.parseFloat(price) > 100);
    }

}