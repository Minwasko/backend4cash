package ee.vovtech.backend4cash.service.currency;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.sql.Time;
import java.sql.Timestamp;

public class CoingeckoAPI {

    // Api url to get coins price
    private final static String PRICE_URL = "https://api.coingecko.com/api/v3/coins";
    private final static String DEFAULT_CURRENCY = "usd";

    public static void updateCurrencies(){

        try {
            System.out.println(getCurrencyPrice("bitcoin").getObject().getJSONObject("prices").toString());
        } catch (UnirestException e) {
            e.printStackTrace();
        }

    }

    private static JsonNode getCurrencyPrice(String id) throws UnirestException {

        return Unirest.get(PRICE_URL)
                .queryString("id", id)
                .queryString("vs_currency", DEFAULT_CURRENCY)
                .queryString("from", new Timestamp(System.currentTimeMillis() - 10_000))
                .queryString("to", new Timestamp(System.currentTimeMillis()))
                .asJson().getBody();


    }

}
