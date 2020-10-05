package ee.vovtech.backend4cash.service;

import ee.vovtech.backend4cash.exceptions.CurrencyNotFoundException;
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
}
