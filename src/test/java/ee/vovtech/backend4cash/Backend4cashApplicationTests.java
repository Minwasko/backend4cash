package ee.vovtech.backend4cash;

import ee.vovtech.backend4cash.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Backend4cashApplicationTests {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Test
    void name() {
        String token = jwtTokenProvider.createTokenForTests("meme");
        System.out.println(token);
    }
}
