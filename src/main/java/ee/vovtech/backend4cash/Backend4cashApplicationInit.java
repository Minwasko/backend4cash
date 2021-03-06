package ee.vovtech.backend4cash;

import com.mashape.unirest.http.exceptions.UnirestException;
import ee.vovtech.backend4cash.model.Currency;
import ee.vovtech.backend4cash.model.User;
import ee.vovtech.backend4cash.repository.CurrencyRepository;
import ee.vovtech.backend4cash.repository.UserRepository;
import ee.vovtech.backend4cash.security.DbRole;
import ee.vovtech.backend4cash.service.currency.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws UnirestException {

        List<Currency> coins = currencyService.initCoins(); // get data from server
        currencyRepository.saveAll(coins); // adding coins to database

        if(!userRepository.existsById(1L)) {
            User user = new User();
            user.setCash("100");
            user.setUsername("vovan");
            user.setEmail("vovan@vovtech.com");
            user.setPassword(passwordEncoder.encode("vovan"));
            user.setRole(DbRole.ADMIN);
            userRepository.save(user);
        }
    }
}
