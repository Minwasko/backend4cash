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
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.yaml")
class UserControllerTest extends RestTemplateTests {


    public static final ParameterizedTypeReference<List<User>> LIST_OF_USERS =
            new ParameterizedTypeReference<>() {};
    public static final ParameterizedTypeReference<List<ForumPost>> LIST_OF_POSTS =
            new ParameterizedTypeReference<>() {};

    @Autowired
    private TestRestTemplate testRestTemplate;

    private String adminToken;
    private String userToken;
    private long adminId;
    private long userId;

    @BeforeAll
    void getTokens() {
        forumPostRepository.deleteAll();
        userRepository.deleteAll();
        adminToken = getAdminToken();
        userToken = getUserToken();
        adminId = userRepository.findAll().get(0).getId();
        userId = userRepository.findAll().get(1).getId();
        System.out.println(userRepository.findAll().stream().map(User::getRole));
        System.out.println(userRepository.findAll().stream().map(User::getId).collect(Collectors.toList()));
        // admin - 6
        // user - 7
    }

    @Test
    void getUserRequiresCorrectRole() {
        // id=2 -> admin role
        // id=3 -> user role
        // admin can get all both users' info
        ResponseEntity<LoggedInUserDto> userOneFromAdmin = testRestTemplate.exchange("/users/" + adminId,
                HttpMethod.GET, new HttpEntity<>(authorizationHeader(adminToken)), LoggedInUserDto.class);
        assertOk(userOneFromAdmin);
        ResponseEntity<LoggedInUserDto> userTwoFromAdmin = testRestTemplate.exchange("/users/" + userId,
                HttpMethod.GET, new HttpEntity<>(authorizationHeader(adminToken)), LoggedInUserDto.class);
        assertOk(userTwoFromAdmin);
        // user can only access his own info
        ResponseEntity<LoggedInUserDto> userOneFromUser = testRestTemplate.exchange("/users/" + adminId,
                HttpMethod.GET, new HttpEntity<>(authorizationHeader(userToken)), LoggedInUserDto.class);
        assertEquals(400, userOneFromUser.getStatusCodeValue());
        ResponseEntity<LoggedInUserDto> userTwoFromUser = testRestTemplate.exchange("/users/" + userId,
                HttpMethod.GET, new HttpEntity<>(authorizationHeader(userToken)), LoggedInUserDto.class);
        assertOk(userTwoFromUser);
    }

    @Test
    void getUsersNeedCorrectRights() {
        // normal users dont have access to this
        try {
            ResponseEntity<List<User>> exchangeFail = testRestTemplate.exchange("/users",
                    HttpMethod.GET, new HttpEntity<>(authorizationHeader(userToken)), LIST_OF_USERS);
        } catch (Exception e) {
            // Exception has to be thrown since user has no rights..........
            assertEquals(e.getClass(), RestClientException.class);
        }
        // ensure for admin users are not null
        ResponseEntity<List<User>> exchange = testRestTemplate.exchange("/users",
                HttpMethod.GET, new HttpEntity<>(authorizationHeader(adminToken)), LIST_OF_USERS);
        List<User> users = assertOk(exchange);
        assertNotNull(users);
    }


}