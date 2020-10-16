package ee.vovtech.backend4cash.controller;

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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CurrencyPriceControllerTest {

    public static final ParameterizedTypeReference<List<Currency>> LIST_OF_CURRENCIES =
            new ParameterizedTypeReference<>() {};
    public static final ParameterizedTypeReference<List<TimestampPrice>> LIST_OF_PRICES =
            new ParameterizedTypeReference<>() {};

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void currencyPricesAreNotEmpty() {
        ResponseEntity<List<Currency>> exchange = testRestTemplate.exchange("/coins", HttpMethod.GET, null, LIST_OF_CURRENCIES);
        System.out.println(exchange.getBody());
        assertNotNull(exchange.getBody());

        Currency currency = exchange.getBody().get(0);
        ResponseEntity<List<TimestampPrice>> exchangePrices = testRestTemplate.exchange("/coins/" + currency.getName() + "/pricedata", HttpMethod.GET, null, LIST_OF_PRICES);
        System.out.println(exchange.getBody());
        assertNotNull(exchange.getBody());
    }

    @Test
    void currencyNameMatchesPrices(){

        ResponseEntity<List<Currency>> exchange = testRestTemplate.exchange("/coins", HttpMethod.GET, null, LIST_OF_CURRENCIES);
        System.out.println(exchange.getBody());
        assertNotNull(exchange.getBody());

        Currency currency = exchange.getBody().get(0);
        ResponseEntity<List<TimestampPrice>> exchangePrices = testRestTemplate.exchange("/coins/" + currency.getName() + "/pricedata", HttpMethod.GET, null, LIST_OF_PRICES);
        System.out.println(exchange.getBody());
        assertNotNull(exchange.getBody());

        assertEquals(currency.getName(), exchange.getBody().get(0).getName());

    }

    @Ignore
    // TODO fix update method
    void changeCurrencyPricesChangesCurrencyPrices(){
        ResponseEntity<List<Currency>> exchange = testRestTemplate.exchange("/coins", HttpMethod.GET, null, LIST_OF_CURRENCIES);
        assertNotNull(exchange.getBody());

        Currency currency = exchange.getBody().get(0);
        TimestampPrice helo = new TimestampPrice(currency, 10, "10");
        testRestTemplate.exchange("/coins/" +
                currency.getName() + "/pricedata", HttpMethod.PUT, new HttpEntity<>(new ArrayList<>()), Currency.class);

        ResponseEntity<List<TimestampPrice>> exchangePrices = testRestTemplate.exchange("/coins/" + currency.getName() + "/pricedata", HttpMethod.GET, null, LIST_OF_PRICES);
        System.out.println(exchange.getBody());
        assertNotNull(exchangePrices.getBody());

        assertEquals(new ArrayList<>(), exchangePrices.getBody());

    }



}