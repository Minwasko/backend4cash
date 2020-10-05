package ee.vovtech.backend4cash.service;

import ee.vovtech.backend4cash.exceptions.CurrencyNotFoundException;
import ee.vovtech.backend4cash.exceptions.InvalidCurrencyException;
import ee.vovtech.backend4cash.model.Currency;
import ee.vovtech.backend4cash.model.CurrencyPrice;
import ee.vovtech.backend4cash.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


    public Currency updateCurrency(String currencyId, Currency currency) {
        if (currencyId == null || currencyRepository.findById(currencyId).isEmpty()) {
            throw new InvalidCurrencyException("Currency is not present in the database");
        } else if (currency.getCurrencyPrice().getDatePriceMap() == null) {
            throw new InvalidCurrencyException("Currency price data is empty");
        } else if (!currencyId.equals(currency.getName())) {
            throw new InvalidCurrencyException("Currencies are different");
        }
        Currency dbCurrency = findById(currencyId);
        dbCurrency.setCurrencyPrice(currency.getCurrencyPrice());
        dbCurrency.setDescription(currency.getDescription());
        dbCurrency.setHomepageLink(currency.getHomepageLink());
        dbCurrency.setImageRef(currency.getImageRef());
        return currencyRepository.save(dbCurrency);
    }

    public void delete(String id) {
        if (findById(id) == null) {
            throw new CurrencyNotFoundException();
        }
        currencyRepository.delete(findById(id));
    }

    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

}
