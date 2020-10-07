package ee.vovtech.backend4cash.service.currency;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;

@Service
public class CoingeckoAPI {

    // Api url to get coins price
    private final static String PRICE_URL = "https://api.coingecko.com/api/v3/coins/";
    private final static String DEFAULT_CURRENCY = "usd";
    private static final Logger log = LoggerFactory.getLogger(CoingeckoAPI.class);



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
