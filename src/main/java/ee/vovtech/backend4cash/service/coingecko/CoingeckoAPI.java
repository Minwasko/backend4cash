package ee.vovtech.backend4cash.service.coingecko;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mysql.cj.xdevapi.JsonArray;
import ee.vovtech.backend4cash.repository.CurrencyRepository;
import ee.vovtech.backend4cash.service.currency.CurrencyService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
public class CoingeckoAPI {

    @Autowired
    CurrencyService currencyService;
    @Autowired
    CurrencyRepository currencyRepository;

    // Our coins "top 10 coins"
    // https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=10page=1&sparkline=false

    // Api url to get coins price
    private final static String COINS_URL = "https://api.coingecko.com/api/v3/coins/";
    private final static String DEFAULT_CURRENCY = "usd";
    public final static int AMOUNT_OF_CURRENCIES = 8;
    private final static int MONTH_SECONDS = 2_592_000;
    private static final Logger log = LoggerFactory.getLogger(CoingeckoAPI.class);
    // TODO: refactor stuff here
    // get top 10 currencies, amount changed as a variable

    public static JSONArray getTopCurrencies() throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.get(COINS_URL + "markets?")
                .queryString("vs_currency", DEFAULT_CURRENCY)
                .queryString("order", "market_cap_decs")
                .queryString("per_page", AMOUNT_OF_CURRENCIES)
                .queryString("page", 1)
                .queryString("sparkline", "false").asJson();
        return response.getBody().getArray();
    }

    // get data for exact currency
    public static JSONObject getCurrency(String id) throws UnirestException {
        HttpResponse<JsonNode> response1 = Unirest.get(COINS_URL + id + "?")
                .queryString("localization", "false")
                .queryString("tickers", "false")
                .queryString("market_data", "true")
                .queryString("community_data", "false")
                .queryString("developer_data", "false")
                .queryString("sparkline", "false")
                .asJson();
        return response1.getBody().getObject();
    }

    public static JSONArray getCurrencyPriceLastHour(String id) throws UnirestException {
        return getPriceDataBetween(id, Instant.now().getEpochSecond() - 3600, Instant.now().getEpochSecond());

    }

    public static JSONArray getCurrencyPriceLastDay(String id) throws UnirestException {
        return getPriceDataBetween(id, Instant.now().getEpochSecond() - 86400, Instant.now().getEpochSecond());

    }


    private static JSONArray getPriceDataBetween(String id, long from, long to) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.get(COINS_URL + id + "/market_chart/range?")
                .queryString("vs_currency", DEFAULT_CURRENCY)
                .queryString("from", from)
                .queryString("to", to)
                .asJson();
        // return only needed shit and only that
        return response.getBody().getObject().getJSONArray("prices");
    }



}
