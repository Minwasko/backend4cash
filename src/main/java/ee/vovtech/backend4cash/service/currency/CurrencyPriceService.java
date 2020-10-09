package ee.vovtech.backend4cash.service.currency;

import com.mashape.unirest.http.exceptions.UnirestException;
import ee.vovtech.backend4cash.exceptions.InvalidCurrencyException;
import ee.vovtech.backend4cash.model.Currency;
import ee.vovtech.backend4cash.model.TimestampPrice;
import ee.vovtech.backend4cash.repository.CurrencyRepository;
import ee.vovtech.backend4cash.service.coingecko.CoingeckoAPI;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
            priceData.put(datePrice.getLong(0) / 1000, datePrice.get(1).toString());
        }
        return priceData;
    }

    public Currency update(String id, List<TimestampPrice> timestampPrices) {
        if (currencyRepository.findById(id).isEmpty()) {
            throw new InvalidCurrencyException("No such currency exception");
        } else if (timestampPrices.isEmpty()) {
            throw new InvalidCurrencyException("New price data is empty");
        } else if (!id.equals(timestampPrices.get(0).getCurrency().getName())) {
            throw new InvalidCurrencyException("Price data is for a different currency");
        }
        Currency dbCurrency = currencyRepository.findById(id).get();
        dbCurrency.setTimestampPrices(timestampPrices);
        return currencyRepository.save(dbCurrency);
    }

    public Map<Long, String> getPriceBetween(String id, long from, long to) {
        return currencyService.findById(id).getTimestampPrices().stream()
                .filter(e -> e.getTimestamp() >= from && e.getTimestamp() <= to)
                .collect(Collectors.toMap(TimestampPrice::getTimestamp, TimestampPrice::getPrice));
        }
    }

