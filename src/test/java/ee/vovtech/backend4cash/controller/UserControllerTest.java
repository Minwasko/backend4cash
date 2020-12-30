package ee.vovtech.backend4cash.controller;

import ee.vovtech.backend4cash.RestTemplateTests;
import ee.vovtech.backend4cash.dto.LoggedInUserDto;
import ee.vovtech.backend4cash.model.Currency;
import ee.vovtech.backend4cash.model.ForumPost;
import ee.vovtech.backend4cash.model.User;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest extends RestTemplateTests {


    public static final ParameterizedTypeReference<List<User>> LIST_OF_USERS =
            new ParameterizedTypeReference<>() {};
    public static final ParameterizedTypeReference<List<ForumPost>> LIST_OF_POSTS =
            new ParameterizedTypeReference<>() {};

    @Autowired
    private TestRestTemplate testRestTemplate;

    private String adminToken;
    private String userToken;

    @BeforeAll
    void getTokens() {
        adminToken = getAdminToken();
        userToken = getUserToken();
    }

    @Test
    void getUserRequiresCorrectRole() {
        // id=2 -> admin role
        // id=3 -> user role
        // admin can get all both users' info
        ResponseEntity<LoggedInUserDto> userOneFromAdmin = testRestTemplate.exchange("/users/2",
                HttpMethod.GET, new HttpEntity<>(authorizationHeader(adminToken)), LoggedInUserDto.class);
        assertOk(userOneFromAdmin);
        ResponseEntity<LoggedInUserDto> userTwoFromAdmin = testRestTemplate.exchange("/users/3",
                HttpMethod.GET, new HttpEntity<>(authorizationHeader(adminToken)), LoggedInUserDto.class);
        assertOk(userTwoFromAdmin);
        // user can only access his own info
        ResponseEntity<LoggedInUserDto> userOneFromUser = testRestTemplate.exchange("/users/2",
                HttpMethod.GET, new HttpEntity<>(authorizationHeader(userToken)), LoggedInUserDto.class);
        assertEquals(400, userOneFromUser.getStatusCodeValue());
        ResponseEntity<LoggedInUserDto> userTwoFromUser = testRestTemplate.exchange("/users/3",
                HttpMethod.GET, new HttpEntity<>(authorizationHeader(userToken)), LoggedInUserDto.class);
        assertOk(userTwoFromUser);
    }

    @Test
    void getUsersNeedCorrectRights() {
        // normal users dont have access to this
        ResponseEntity<List<User>> exchangeFail = testRestTemplate.exchange("/users",
                HttpMethod.GET, new HttpEntity<>(authorizationHeader(userToken)), LIST_OF_USERS);
        assertEquals(400, exchangeFail.getStatusCodeValue());
        // ensure for admin users are not null
        ResponseEntity<List<User>> exchange = testRestTemplate.exchange("/users",
                HttpMethod.GET, new HttpEntity<>(authorizationHeader(adminToken)), LIST_OF_USERS);
        List<User> users = assertOk(exchange);
        assertNotNull(users);
    }


}