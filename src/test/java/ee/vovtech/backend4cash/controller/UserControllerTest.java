package ee.vovtech.backend4cash.controller;

import ee.vovtech.backend4cash.model.Currency;
import ee.vovtech.backend4cash.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {


    public static final ParameterizedTypeReference<List<User>> LIST_OF_USERS =
            new ParameterizedTypeReference<>() {};
    public static long TEST_USER_ID;


    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void usersAreNotEmpty() {
        ResponseEntity<List<User>> exchange = testRestTemplate.exchange("/users", HttpMethod.GET, null, LIST_OF_USERS);
        assertNotNull(exchange.getBody());
    }

    @Test
    void getExactUser() {
        ResponseEntity<List<User>> exchange = testRestTemplate.exchange("/users", HttpMethod.GET, null, LIST_OF_USERS);
        assertNotNull(exchange.getBody());
        User dbUser = exchange.getBody().get(0);
        ResponseEntity<User> exchangeUser = testRestTemplate
                .exchange("/users/" + dbUser.getId(), HttpMethod.GET, null, User.class);
        User user = exchangeUser.getBody();
        assertNotNull(user);
        assertEquals(dbUser.getId(), user.getId());
        assertEquals("meme", user.getNickname());
    }

    @Test
    void saveUser() {
        User user = new User((long) 999, "testUser", new ArrayList<>());
        ResponseEntity<User> exchange = testRestTemplate.exchange("/users", HttpMethod.POST, new HttpEntity<>(user), User.class);
        User dbUser = exchange.getBody();
        assertNotNull(dbUser);
        assertEquals("testUser", dbUser.getNickname());
        TEST_USER_ID = dbUser.getId();
    }

    @Test
    void updateUser() {
        ResponseEntity<User> exchange = testRestTemplate.exchange("/users/" + TEST_USER_ID , HttpMethod.GET, null, User.class);
        User dbUser = exchange.getBody();
        assertNotNull(dbUser);
        dbUser.setNickname("testUserUpdated");
        ResponseEntity<User> exchangeUpdated = testRestTemplate.exchange("/coins/" + dbUser.getId(), HttpMethod.PUT, new HttpEntity<>(dbUser), User.class);
        User dbUserUpdated = exchangeUpdated.getBody();
        assertNotNull(dbUserUpdated);
        assertEquals("testUserUpdated", dbUserUpdated.getNickname());
    }

    @Test
    void deleteUser() {

    }

}