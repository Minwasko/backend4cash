package ee.vovtech.backend4cash.service.currency;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.mysql.cj.xdevapi.JsonArray;
import ee.vovtech.backend4cash.exceptions.CurrencyNotFoundException;
import ee.vovtech.backend4cash.exceptions.InvalidCurrencyException;
import ee.vovtech.backend4cash.model.Currency;
import ee.vovtech.backend4cash.model.CurrencyPrice;
import ee.vovtech.backend4cash.repository.CurrencyRepository;
import ee.vovtech.backend4cash.service.coingecko.CoingeckoAPI;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class CurrencyPriceService {

    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private CurrencyService currencyService;

    public Map<Long, String> fillPriceData(String id) throws UnirestException {
        JSONArray data = CoingeckoAPI.getPriceData(id).getJSONArray("prices"); // getting json with price data
        LinkedHashMap<Long, String> priceData = new LinkedHashMap<>();
        for (int i = 0; i < data.length(); i++) { // add key=date value=price to map
            JSONArray datePrice = data.getJSONArray(i);
            Long timestamp = datePrice.getLong(0) / 1000;
            String price = datePrice.get(1).toString();
            priceData.put(timestamp, price);

        }
        return priceData;
    }

    public CurrencyPrice findById(String id) {
        if (currencyRepository.findById(id).isPresent()) {
            return currencyRepository.findById(id).get().getCurrencyPrice();
        }
        throw new CurrencyNotFoundException();
    }

    public Currency update(String id, CurrencyPrice price) {
        if (currencyRepository.findById(id).isEmpty()) {
            throw new InvalidCurrencyException("No such currency exception");
        } else if (price.getDatePriceMap().isEmpty()) {
            throw new InvalidCurrencyException("New price data is empty");
        } else if (!id.equals(price.getName())) {
            throw new InvalidCurrencyException("Price data is for a different currency");
        }
        Currency dbCurrency = currencyRepository.findById(id).get();
        dbCurrency.setCurrencyPrice(price);
        return currencyRepository.save(dbCurrency);
    }

    public Map<Long, String> getPriceBetween(String id, long from, long to) {
        return currencyService.findById(id).getCurrencyPrice().getDatePriceMap().entrySet().stream()
                .filter(e -> e.getKey() >= from && e.getKey() <= to)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
    }

