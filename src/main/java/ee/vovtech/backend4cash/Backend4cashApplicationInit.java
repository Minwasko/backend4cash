package ee.vovtech.backend4cash;

import ee.vovtech.backend4cash.model.Currency;
import ee.vovtech.backend4cash.model.CurrencyPrice;
import ee.vovtech.backend4cash.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Backend4cashApplicationInit implements CommandLineRunner {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    public void run(String... args) {
//        Map<Currency, String> coins = new HashMap<>();
//        coins.put(new Currency(), "bitcoin");
        Currency coin = new Currency();
        coin.setName("bitcoin");
        coin.setImageRef("imageHref");
        coin.setHomepageLink("HomepageLink");
        coin.setDescription("description");
        CurrencyPrice price = new CurrencyPrice();
        price.setName(coin.getName());
        price.setDatePriceMap(new HashMap<>());
        coin.setCurrencyPrice(price);
        List<Currency> coins = List.of(coin);
        currencyRepository.saveAll(coins);
    }
}
