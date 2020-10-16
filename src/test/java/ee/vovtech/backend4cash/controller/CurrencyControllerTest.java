package ee.vovtech.backend4cash.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import ee.vovtech.backend4cash.model.Currency;
import ee.vovtech.backend4cash.model.TimestampPrice;
import ee.vovtech.backend4cash.repository.CurrencyRepository;
import ee.vovtech.backend4cash.service.currency.CurrencyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CurrencyControllerTest {

    public static final ParameterizedTypeReference<List<Currency>> LIST_OF_CURRENCIES =
            new ParameterizedTypeReference<>() {};

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void coinsAreNotEmpty() {
        ResponseEntity<List<Currency>> exchange = testRestTemplate.exchange("/coins", HttpMethod.GET, null, LIST_OF_CURRENCIES);
        System.out.println(exchange.getBody());
        assertNotNull(exchange.getBody());
    }

    @Test
    void queryForExactCoin() {
        ResponseEntity<Currency> exchange = testRestTemplate.exchange("/coins/" + "bitcoin", HttpMethod.GET, null, Currency.class);
        Currency coin = exchange.getBody();
        assertNotNull(coin);
        // assert values exist
        assertEquals("bitcoin", coin.getName());
        assertNotNull(coin.getHomepageLink());
        assertNotNull(coin.getImageRef());
    }

    @Test
    void saveNewCoin() {
        Currency currency = new Currency();
        currency.setName("testCurrency");
        currency.setImageRef("imageRef");
        currency.setHomepageLink("homePageLink");
        currency.setTimestampPrices(List.of(new TimestampPrice(currency, 10, "10")));
        ResponseEntity<Currency> exchange = testRestTemplate.exchange("/coins", HttpMethod.POST, new HttpEntity<>(currency), Currency.class);
        Currency receivedCurrency = exchange.getBody();
        assertNotNull(receivedCurrency);
        assertEquals("testCurrency", receivedCurrency.getName());
        assertEquals("homePageLink", receivedCurrency.getHomepageLink());
        assertEquals("imageRef", receivedCurrency.getImageRef());
    }

    @Test
    void updateNewCoin() {
        Currency currency = new Currency();
        currency.setName("testCurrency");
        currency.setImageRef("imageRefUpdated");
        currency.setHomepageLink("homePageLinkUpdated");
        currency.setTimestampPrices(List.of(new TimestampPrice(currency, 10, "10")));
        ResponseEntity<Currency> exchange = testRestTemplate.exchange("/coins/" + "testCurrency", HttpMethod.PUT, new HttpEntity<>(currency), Currency.class);
        Currency updatedCurrency = exchange.getBody();
        assertNotNull(updatedCurrency);
        System.out.println(updatedCurrency);
        assertEquals("testCurrency", updatedCurrency.getName());
        assertEquals("homePageLinkUpdated", updatedCurrency.getHomepageLink());
        assertEquals("imageRefUpdated", updatedCurrency.getImageRef());
    }

    @Test
    void deleteNewCoin() {
        ResponseEntity<Currency> exchange = testRestTemplate.exchange("/coins/" + "testCurrency", HttpMethod.GET, null, Currency.class);
        Currency currency = exchange.getBody();
        assertNotNull(currency);
        testRestTemplate.exchange("/coins/" + "testCurrency", HttpMethod.DELETE, null, Currency.class);
        ResponseEntity<List<Currency>> newCoinsList = testRestTemplate.exchange("/coins", HttpMethod.GET, null, LIST_OF_CURRENCIES);
        List<Currency> currencies = newCoinsList.getBody();
        assertNotNull(currencies);
        assertFalse(currencies.contains(currency));
    }

}