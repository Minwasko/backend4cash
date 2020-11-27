package ee.vovtech.backend4cash.service.currency;

import ee.vovtech.backend4cash.exceptions.InvalidCurrencyException;
import ee.vovtech.backend4cash.model.Currency;
import ee.vovtech.backend4cash.model.TimestampPrice;
import ee.vovtech.backend4cash.model.User;
import ee.vovtech.backend4cash.repository.CurrencyRepository;
import ee.vovtech.backend4cash.service.coingecko.CoingeckoAPI;
import ee.vovtech.backend4cash.service.user.UserService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class CurrencyPriceService {

    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private UserService userService;

    public enum UpdateTime {DAY, HOUR}

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

    // TODO fix this sh*t (sorry za mat)
    public Currency updateDB(String id, List<TimestampPrice> timestampPrices) {
        if (currencyRepository.findById(id).isEmpty()) throw new InvalidCurrencyException("No such currency exception");
        else if (timestampPrices.isEmpty()) throw new InvalidCurrencyException("New price data is empty");
        Currency dbCurrency = currencyRepository.findById(id).get();
        dbCurrency.setTimestampPrices(timestampPrices);
        currencyRepository.save(dbCurrency);
        return dbCurrency;
    }

    public String getCurrentPrice(String id) {
        Currency dbCurrency = currencyService.findById(id);
        return dbCurrency.getTimestampPrices().get(dbCurrency.getTimestampPrices().size() - 1).getPrice();
    }

    public boolean tryToBuyCoins(long userId, String coinId, String amount) {
        User dbUser = userService.findById(userId);
        BigDecimal totalPrice = new BigDecimal(getCurrentPrice(coinId)).multiply(new BigDecimal(amount));
        if (totalPrice.compareTo(new BigDecimal(dbUser.getCash())) < 0) {
            dbUser.addCoins(coinId, amount);
            dbUser.setCash(new BigDecimal(dbUser.getCash()).subtract(totalPrice).toString());
            userService.save(dbUser);
            return true;
        }
        return false;
    }

    public boolean tryToSellCoins(long userId, String coinId, String amount) {
        User dbUser = userService.findById(userId);
        String coinPrice = getCurrentPrice(coinId);
        AbstractMap.SimpleEntry<String, String> userCoin = dbUser.getOwnedCoinByCoinId(coinId);
        if (userCoin == null) return false;
        BigDecimal amountLeft = new BigDecimal(userCoin.getValue()).subtract(new BigDecimal(amount));
        if (amountLeft.compareTo(BigDecimal.ZERO) >= 0) {
            dbUser.setCash(new BigDecimal(coinPrice).multiply(new BigDecimal(amount)).add(new BigDecimal(dbUser.getCash())).toString());
            dbUser.setCoinsAmount(coinId, amountLeft.toString());
            userService.save(dbUser);
            return true;
        }
        return false;
    }
}

