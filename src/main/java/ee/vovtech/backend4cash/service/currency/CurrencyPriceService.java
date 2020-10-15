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

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CurrencyPriceService {

    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private CurrencyService currencyService;

    public enum UpdateTime {DAY, HOUR};

    public void updatePrice(String id, UpdateTime updateTime) {
        JSONArray data;
        if (updateTime.equals(UpdateTime.DAY)) data = CoingeckoAPI.getCurrencyPriceLastDay(id);
        else data = CoingeckoAPI.getCurrencyPriceLastHour(id);

        List<TimestampPrice> timestampPrices = new ArrayList<>();
        Currency toUpdate = currencyService.findById(id);

        for(int i = 0; i < data.length(); i++) {
            JSONArray timestampPrice = data.getJSONArray(i);
            timestampPrices.add(new TimestampPrice(toUpdate, timestampPrice.getLong(0) / 1000, timestampPrice.get(1).toString()));
        }
        updateDB(id, timestampPrices);

    }

    public Currency updateDB(String id, List<TimestampPrice> timestampPrices) {
        if (currencyRepository.findById(id).isEmpty()) {
            throw new InvalidCurrencyException("No such currency exception");
        } else if (timestampPrices.isEmpty()) {
            throw new InvalidCurrencyException("New price data is empty");
        } else if (!id.equals(timestampPrices.get(0).getCurrency().getName())) {
            throw new InvalidCurrencyException("Price data is for a different currency");
        }
        Currency dbCurrency = currencyRepository.findById(id).get();
        dbCurrency.setTimestampPrices(timestampPrices);
        currencyRepository.save(dbCurrency);
        return dbCurrency;
    }
}

