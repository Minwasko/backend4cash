package ee.vovtech.backend4cash.service;

import ee.vovtech.backend4cash.exceptions.CurrencyNotFoundException;
import ee.vovtech.backend4cash.exceptions.InvalidCurrencyException;
import ee.vovtech.backend4cash.model.Currency;
import ee.vovtech.backend4cash.model.CurrencyPrice;
import ee.vovtech.backend4cash.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    public Currency findById(String id) {
        return currencyRepository.findById(id).orElseThrow(CurrencyNotFoundException::new);
    }

    public Currency save(Currency currency) {
        if (currency.getName() == null) {
            throw new InvalidCurrencyException("Currency is undefined");
        } else if (currency.getCurrencyPrice() == null) {
            throw new InvalidCurrencyException("Currency has no price data");
        }
        return currencyRepository.save(currency);
    }


    public Currency updatePrice(String currencyId, CurrencyPrice price) {
        if (currencyId == null) {
            throw new InvalidCurrencyException("Currency is undefined");
        } else if (price.getDatePriceMap() == null) {
            throw new InvalidCurrencyException("Currency price data is empty");
        } else if (!currencyId.equals(price.getName())) {
            throw new InvalidCurrencyException("Price data is for a different currency");
        }
        Currency dbCurrency = findById(currencyId);
        dbCurrency.setCurrencyPrice(price);
        return currencyRepository.save(dbCurrency);
    }

}
