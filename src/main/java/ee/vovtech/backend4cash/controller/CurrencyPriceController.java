package ee.vovtech.backend4cash.controller;


import ee.vovtech.backend4cash.model.CurrencyPrice;
import ee.vovtech.backend4cash.service.CurrencyPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyPriceController {

    @Autowired
    CurrencyPriceService currencyPriceService;


    @GetMapping("/coins/{id}/pricedata")
    public CurrencyPrice getCurrencyPriceData(@PathVariable String id) {
        return currencyPriceService.findById(id);
    }
}
