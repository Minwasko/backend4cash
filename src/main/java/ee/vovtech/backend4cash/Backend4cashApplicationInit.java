package ee.vovtech.backend4cash;

import com.mashape.unirest.http.exceptions.UnirestException;
import ee.vovtech.backend4cash.model.Currency;
import ee.vovtech.backend4cash.model.ForumPost;
import ee.vovtech.backend4cash.model.User;
import ee.vovtech.backend4cash.repository.CurrencyRepository;
import ee.vovtech.backend4cash.repository.ForumPostRepository;
import ee.vovtech.backend4cash.repository.UserRepository;
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
    @Autowired
    private ForumPostRepository forumPostRepository;

    @Override
    public void run(String... args) throws UnirestException {

        List<Currency> coins = currencyService.initCoins(); // get data from server
        currencyRepository.saveAll(coins); // adding coins to database

        User user = new User();
        user.setId(1);
        user.setCash("100");
        user.setNickname("meme");
        user.setEmail("kek@kek.ee");
        user.setPassword("12345lol");
        userRepository.save(user);
        ForumPost forumPost = new ForumPost("good", user);
        forumPostRepository.save(forumPost);
    }
}
