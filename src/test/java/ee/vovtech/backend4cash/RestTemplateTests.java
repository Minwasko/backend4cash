package ee.vovtech.backend4cash;

import ee.vovtech.backend4cash.dto.LoginDto;
import ee.vovtech.backend4cash.dto.LoginResponse;
import ee.vovtech.backend4cash.model.User;
import ee.vovtech.backend4cash.repository.ForumPostRepository;
import ee.vovtech.backend4cash.repository.NewsRepository;
import ee.vovtech.backend4cash.repository.UserRepository;
import ee.vovtech.backend4cash.security.DbRole;
import ee.vovtech.backend4cash.security.JwtTokenProvider;
import ee.vovtech.backend4cash.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class RestTemplateTests {

    @Autowired
    protected TestRestTemplate testRestTemplate;
    @Autowired
    protected JwtTokenProvider jwtTokenProvider;
    @Autowired
    protected UserService userService;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ForumPostRepository forumPostRepository;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    protected NewsRepository newsRepository;



    public <T> T assertOk(ResponseEntity<T> exchange) {
        assertNotNull(exchange.getBody());
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        return exchange.getBody();
    }

    protected HttpHeaders authorizationHeader(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        return headers;
    }

    public String getAdminToken() {
        // creating a test admin
        User admin = new User();
        admin.setEmail("admin@admin.admin");
        admin.setId(2);
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setCash("100000");
        admin.setStatus("adminStatus");
        admin.setRole(DbRole.ADMIN);
        admin.setOwnedCoins(new ArrayList<>());
        userService.save(admin);
        // login admin to get Token from response
        LoginDto adminCredentials = new LoginDto("admin@admin.admin", "admin");
        ResponseEntity<LoginResponse> exchange = testRestTemplate.exchange("/users/login", HttpMethod.POST,
                new HttpEntity<>(adminCredentials), LoginResponse.class);
        LoginResponse response = assertOk(exchange);
        return "Bearer " + response.getToken();
    }

    public String getUserToken() {
        // creating a test user
        User user = new User();
        user.setEmail("user@user.user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setCash("100000");
        user.setStatus("userStatus");
        user.setRole(DbRole.USER);
        user.setId(3);
        user.setOwnedCoins(new ArrayList<>());
        userService.save(user);
        // login admin to get Token from response
        LoginDto userCredentials = new LoginDto("user@user.user", "user");
        ResponseEntity<LoginResponse> exchange = testRestTemplate.exchange("/users/login", HttpMethod.POST,
                new HttpEntity<>(userCredentials), LoginResponse.class);
        LoginResponse response = assertOk(exchange);
        return "Bearer " + response.getToken();
    }

}

