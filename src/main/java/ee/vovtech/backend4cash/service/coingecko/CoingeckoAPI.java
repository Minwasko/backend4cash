package ee.vovtech.backend4cash.service.coingecko;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
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
    private final static String PRICE_URL = "https://api.coingecko.com/api/v3/coins/";
    private final static String DEFAULT_CURRENCY = "usd";
    public final static int AMOUNT_OF_CURRENCIES = 8;
    private final static int SECONDS_TO_FROM_PRICE_DATA = 2_592_000;
    private static final Logger log = LoggerFactory.getLogger(CoingeckoAPI.class);

    // get top 10 currencies, amount changed as a variable
    public static JSONArray getTopCurrencies() throws UnirestException {
        HttpResponse<JsonNode> response = Unirest
                .get("https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page="
                        + AMOUNT_OF_CURRENCIES + "&page=1&sparkline=false")
                .asJson();
        return response.getBody().getArray();
    }

    // get data for exact currency
    public static JSONObject getCurrency(String id) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.get("https://api.coingecko.com/api/v3/coins/"
                + id + "?localization=false&tickers=false&market_data=true&community_data=false&developer_data=false&sparkline=false").asJson();
        return response.getBody().getObject();
    }

    // get Price history for exact currency
    public static JSONObject getPriceData(String id) throws UnirestException {
        long unixTimeNow = Instant.now().getEpochSecond();
        HttpResponse<JsonNode> response = Unirest.get("https://api.coingecko.com/api/v3/coins/" + id + "/market_chart/range?vs_currency=usd&from="
                + (unixTimeNow - SECONDS_TO_FROM_PRICE_DATA) +  "&to=" + unixTimeNow).asJson();
        return response.getBody().getObject();
    }

    public static JSONObject getPriceDataBetween(String id, long from, long to) throws UnirestException {
        long unixTimeNow = Instant.now().getEpochSecond();
        HttpResponse<JsonNode> response = Unirest.get("https://api.coingecko.com/api/v3/coins/" + id + "/market_chart/range?vs_currency=usd&from="
                + from +  "&to=" + to).asJson();
        return response.getBody().getObject();
    }


    public static String updateCurrencies(){

        try {
            return getCurrencyPrice("bitcoin").getObject().getJSONArray("prices").toString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return "";
    }

    private static JsonNode getCurrencyPrice(String id) throws UnirestException {

        return Unirest.get(PRICE_URL + id + "/market_chart/range")
                .queryString("vs_currency", DEFAULT_CURRENCY)
                .queryString("from", String.valueOf(Instant.now().getEpochSecond() - 600))
                .queryString("to", String.valueOf(Instant.now().getEpochSecond())).asJson().getBody();


    }

}
