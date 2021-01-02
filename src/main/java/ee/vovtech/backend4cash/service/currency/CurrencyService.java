package ee.vovtech.backend4cash.service.currency;

import com.mashape.unirest.http.exceptions.UnirestException;
import ee.vovtech.backend4cash.exceptions.CurrencyNotFoundException;
import ee.vovtech.backend4cash.exceptions.InvalidCurrencyException;
import ee.vovtech.backend4cash.model.Currency;
import ee.vovtech.backend4cash.repository.CurrencyRepository;
import ee.vovtech.backend4cash.service.coingecko.CoingeckoAPI;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CurrencyService {


    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private CurrencyPriceService currencyPriceService;

    public Currency findById(String id) throws UnirestException {
        if (currencyRepository.findById(id).isPresent()) return currencyRepository.findById(id).get();
        createNewCoin(id);
        return findById(id);
    }

    public Currency save(Currency currency) {
        if (currency.getName() == null) {
            throw new InvalidCurrencyException("Currency is undefined");
        }
        return currencyRepository.save(currency);
    }

    public void delete(String id) throws UnirestException {
        currencyRepository.delete(currencyRepository.findById(id).orElseThrow(CurrencyNotFoundException::new));
    }


    public List<Currency> initCoins() throws UnirestException {
        JSONArray coins = CoingeckoAPI.getTopCurrencies(); // get top 10 coins
        for (int i = 0; i < coins.length(); i++) {
            JSONObject coin = coins.getJSONObject(i);
            log.info("Coin initialized: " + coin.toString());// data of each coin
            String name = coin.get("id").toString();
            if (currencyRepository.findById(name).isEmpty()) createNewCoin(name);
        }
        return currencyRepository.findAll();
    }

    public Currency createNewCoin(String id) throws UnirestException {
        if (currencyRepository.findById(id).isPresent()) { // if the coin is in the db, then there wont be a new one created
            throw new InvalidCurrencyException("Currency already exists");
        }
        JSONObject coin = CoingeckoAPI.getCurrency(id); // get coin data
        try {
            Currency newCoin = new Currency(); // put all the data in a new entity
            newCoin.setName(id);
            newCoin.setHomepageLink(coin.getJSONObject("links").getJSONArray("homepage").get(0).toString());
            newCoin.setImageRef(coin.getJSONObject("image").get("small").toString());
            save(newCoin); // save it to the database
            currencyPriceService.updatePrice(id, CurrencyPriceService.UpdateTime.DAY);
            return newCoin;
        } catch (JSONException e) {
            throw new CurrencyNotFoundException();
        }
    }

    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }
}