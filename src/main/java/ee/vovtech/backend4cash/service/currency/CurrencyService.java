package ee.vovtech.backend4cash.service.currency;

import com.mashape.unirest.http.exceptions.UnirestException;
import ee.vovtech.backend4cash.exceptions.CurrencyNotFoundException;
import ee.vovtech.backend4cash.exceptions.InvalidCurrencyException;
import ee.vovtech.backend4cash.model.Currency;
import ee.vovtech.backend4cash.model.TimestampPrice;
import ee.vovtech.backend4cash.repository.CurrencyRepository;
import ee.vovtech.backend4cash.service.coingecko.CoingeckoAPI;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyService {


    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private CurrencyPriceService currencyPriceService;

    public Currency findById(String id) {
        return currencyRepository.findById(id).orElseThrow(CurrencyNotFoundException::new);
    }

    public Currency save(Currency currency) {
        if (currency.getName() == null) {
            throw new InvalidCurrencyException("Currency is undefined");
        }
        return currencyRepository.save(currency);
    }


    public Currency updateCurrency(String currencyId, Currency currency) {
        if (currencyId == null || currencyRepository.findById(currencyId).isEmpty()) {
            throw new InvalidCurrencyException("Currency is not present in the database");
        }  else if (!currencyId.equals(currency.getName())) {
            throw new InvalidCurrencyException("Currencies are different");
        }
        Currency dbCurrency = findById(currencyId);
        dbCurrency.setTimestampPrices(currency.getTimestampPrices());
        dbCurrency.setDescription(currency.getDescription());
        dbCurrency.setHomepageLink(currency.getHomepageLink());
        dbCurrency.setImageRef(currency.getImageRef());
        return currencyRepository.save(dbCurrency);
    }

    public void delete(String id) {
        if (findById(id) == null) { throw new CurrencyNotFoundException(); }
        currencyRepository.delete(findById(id));
    }


    public List<Currency> updateCoinsData() throws UnirestException { // TODO Currently only adds a new coin!!!!
        JSONArray coins = CoingeckoAPI.getTopCurrencies(); // get top 10 coins
        for (int i = 0; i < coins.length(); i++) {
            JSONObject coin = coins.getJSONObject(i); // data of each coin
            String name = coin.get("id").toString();
            if (currencyRepository.findById(name).isEmpty()) {
                createNewCoin(name);
            }
        }
        return currencyRepository.findAll();
    }

    public Currency createNewCoin(String id) throws UnirestException {
        if (currencyRepository.findById(id).isPresent()) { // if the coin is in the db, then there wont be a new one created
            throw new InvalidCurrencyException("Currency already exists");
        }
        JSONObject coin = CoingeckoAPI.getCurrency(id); // get coin data
        Currency newCoin = new Currency(); // put all the data in a new entity
        newCoin.setName(id);
        newCoin.setHomepageLink(coin.getJSONObject("links").getJSONArray("homepage").get(0).toString());
        // possible TODO add a way to save full description, currently over the char limit(255)
        newCoin.setDescription(id + " with a beautiful description");
        newCoin.setImageRef(coin.getJSONObject("image").get("small").toString());
        Map<Long, String> priceMap = currencyPriceService.fillPriceData(id);
        // make new TimestampPrices and add them all to currency
        List<TimestampPrice> timestampPriceSet = new ArrayList<>();
        for (Map.Entry<Long, String> entry : priceMap.entrySet()) {
            timestampPriceSet.add(new TimestampPrice(newCoin, entry.getKey(), entry.getValue()));
        }
        newCoin.setTimestampPrices(timestampPriceSet);
        save(newCoin); // save it to the database
        return newCoin;
    }

    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

}
