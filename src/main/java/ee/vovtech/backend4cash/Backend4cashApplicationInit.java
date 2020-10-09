package ee.vovtech.backend4cash;

import com.mashape.unirest.http.exceptions.UnirestException;
import ee.vovtech.backend4cash.model.Currency;
import ee.vovtech.backend4cash.model.ForumPost;
import ee.vovtech.backend4cash.model.User;
import ee.vovtech.backend4cash.repository.CurrencyRepository;
import ee.vovtech.backend4cash.repository.UserRepository;
import ee.vovtech.backend4cash.service.currency.CurrencyPriceService;
import ee.vovtech.backend4cash.service.currency.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Backend4cashApplicationInit implements CommandLineRunner {

    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws UnirestException {
//        Map<Currency, String> coins = new HashMap<>();
//        coins.put(new Currency(), "bitcoin");
//        Currency coin = new Currency();
//        coin.setName("bitcoin");
//        coin.setImageRef("imageHref");
//        coin.setHomepageLink("HomepageLink");
//        coin.setDescription("description");
//        CurrencyPrice price = new CurrencyPrice();
//        price.setName(coin.getName());
//        price.setDatePriceMap(new LinkedHashMap<>());
//        coin.setCurrencyPrice(price);
//        List<Currency> coins = List.of(coin);
//        currencyPriceService.fillPriceData("bitcoin");
        List<Currency> coins = currencyService.updateCoinsData(); // get data from server
        currencyRepository.saveAll(coins); // adding coins to database
        User user = new User();
        user.setId(1);
        user.setNickname("meme");
        ForumPost forumPost = new ForumPost("good", user);
        user.addForumPost(forumPost);
        userRepository.save(user);
    }
}
