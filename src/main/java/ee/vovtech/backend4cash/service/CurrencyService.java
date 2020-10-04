package ee.vovtech.backend4cash.service;

import ee.vovtech.backend4cash.exceptions.CurrencyNotFoundException;
import ee.vovtech.backend4cash.model.Currency;
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
}
