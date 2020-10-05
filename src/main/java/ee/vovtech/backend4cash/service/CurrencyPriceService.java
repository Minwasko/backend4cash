package ee.vovtech.backend4cash.service;

import ee.vovtech.backend4cash.exceptions.CurrencyNotFoundException;
import ee.vovtech.backend4cash.exceptions.InvalidCurrencyException;
import ee.vovtech.backend4cash.model.Currency;
import ee.vovtech.backend4cash.model.CurrencyPrice;
import ee.vovtech.backend4cash.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyPriceService {

    @Autowired
    private CurrencyRepository currencyRepository;

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
}
