package ee.vovtech.backend4cash.controller;

import ee.vovtech.backend4cash.RestTemplateTests;
import ee.vovtech.backend4cash.dto.LoggedInUserDto;
import ee.vovtech.backend4cash.model.Currency;
import ee.vovtech.backend4cash.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.yaml")
class CurrencyControllerTest extends RestTemplateTests {

    public static final ParameterizedTypeReference<List<Currency>> LIST_OF_CURRENCIES =
            new ParameterizedTypeReference<>() {};

    private String adminToken;
    private String userToken;
    private long adminId;
    private long userId;

    @BeforeAll
    void getTokens() {
        forumPostRepository.deleteAll();
        userRepository.deleteAll();
        adminToken = getAdminToken();
        userToken = getUserToken();
        adminId = userRepository.findAll().get(0).getId();
        userId = userRepository.findAll().get(1).getId();
        System.out.println(userRepository.findAll().stream().map(User::getRole));
        System.out.println(userRepository.findAll().stream().map(User::getId).collect(Collectors.toList()));
        // admin - 2
        // user - 3
    }

    @Autowired
    private TestRestTemplate testRestTemplate;


    @Test
    void getCurrencyThatDoesntExist() {
        ResponseEntity<Currency> exchange = testRestTemplate.exchange("/coins/" + "thisCurrencyIsNotReal", HttpMethod.GET, null, Currency.class);
        assertEquals(404, exchange.getStatusCodeValue());
    }

    @Test
    void coinsAreNotEmpty() { // coins have ben loaded into db on init
        ResponseEntity<List<Currency>> exchange = testRestTemplate.exchange("/coins", HttpMethod.GET, null, LIST_OF_CURRENCIES);
        List<Currency> currencies = assertOk(exchange);
        assertNotNull(currencies);
    }

    @Test
    void queryForExactCoin() {
        ResponseEntity<Currency> exchange = testRestTemplate.exchange("/coins/" + "bitcoin", HttpMethod.GET, null, Currency.class);
        Currency coin = assertOk(exchange);
        // assert values exist
        assertEquals("bitcoin", coin.getName());
        assertNotNull(coin.getHomepageLink());
        assertNotNull(coin.getImageRef());
    }

    @Test
    void saveNewCoin() { // coin is saved when get method is called for a coin, that is not in the db
        ResponseEntity<Currency> exchange = testRestTemplate.exchange("/coins/" + "dogecoin",
                HttpMethod.GET, null, Currency.class);
        Currency currency = assertOk(exchange);
        assertEquals("dogecoin", currency.getName());
        assertNotNull(currency.getHomepageLink());
        assertNotNull(currency.getImageRef());
    }

    @Test
    void deleteNewCoin() { // to delete we need admin token
        ResponseEntity<Currency> exchange = testRestTemplate.exchange("/coins/" + "dogecoin", HttpMethod.GET, null, Currency.class);
        Currency currency = assertOk(exchange);
        assertEquals("dogecoin", currency.getName());
        testRestTemplate.exchange("/coins/" + "dogecoin", HttpMethod.DELETE, new HttpEntity<>(authorizationHeader(adminToken)), Currency.class);
        ResponseEntity<List<Currency>> newCoinsList = testRestTemplate.exchange("/coins", HttpMethod.GET, null, LIST_OF_CURRENCIES);
        List<Currency> currencies = assertOk(newCoinsList);
        assertFalse(currencies.stream().map(Currency::getName).collect(Collectors.toList()).contains("dogecoin"));
    }

    @Test
    void userCanBuyAndSellCoins(){
        // doing it this way because order is important and junit5 prefers chaos
        buyCoins();
        sellCoins();
    }

    void buyCoins() {
        ResponseEntity<Boolean> exchange = testRestTemplate.exchange("/coins/" + "bitcoin" + "/buy" + "?amount=1&userId=" + userId,
                HttpMethod.PUT, new HttpEntity<>(authorizationHeader(userToken)), Boolean.class);
        Boolean response = assertOk(exchange);
        assertTrue(response);
        // get user info to see his coins and cash
        ResponseEntity<LoggedInUserDto> userExchange = testRestTemplate.exchange("/users/" + userId,
                HttpMethod.GET, new HttpEntity<>(authorizationHeader(userToken)), LoggedInUserDto.class);
        LoggedInUserDto userInfo = assertOk(userExchange);
        assertEquals("bitcoin", userInfo.getOwnedCoins().get(0).getKey());
        assertNotEquals(Float.valueOf("100000"), Float.valueOf(userInfo.getCash()));
        // user cant buy too many coins
        ResponseEntity<Boolean> exchangeFalse = testRestTemplate.exchange("/coins/" + "bitcoin" + "/buy" + "?amount=100000000&userId=" + userId,
                HttpMethod.PUT, new HttpEntity<>(authorizationHeader(userToken)), Boolean.class);
        Boolean responseFalse = assertOk(exchangeFalse);
        assertFalse(responseFalse);
    }

    void sellCoins() {
        ResponseEntity<Boolean> exchange = testRestTemplate.exchange("/coins/" + "bitcoin" + "/sell" + "?amount=1&userId=" + userId,
                HttpMethod.PUT, new HttpEntity<>(authorizationHeader(userToken)), Boolean.class);
        Boolean response = assertOk(exchange);
        assertTrue(response);
        // get user info to see his coins and cash
        ResponseEntity<LoggedInUserDto> userExchange = testRestTemplate.exchange("/users/" + userId,
                HttpMethod.GET, new HttpEntity<>(authorizationHeader(userToken)), LoggedInUserDto.class);
        LoggedInUserDto userInfo = assertOk(userExchange);
        assertEquals(0, userInfo.getOwnedCoins().size());
        assertEquals(Float.valueOf("100000"), Float.valueOf(userInfo.getCash()));
        // user cant sell coins he doesnt have
        ResponseEntity<Boolean> exchangeFalse = testRestTemplate.exchange("/coins/" + "bitcoin" + "/sell" + "?amount=1&userId=" + userId,
                HttpMethod.PUT, new HttpEntity<>(authorizationHeader(userToken)), Boolean.class);
        Boolean responseFalse = assertOk(exchangeFalse);
        assertFalse(responseFalse);
    }
}