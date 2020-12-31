package ee.vovtech.backend4cash.controller;

import ee.vovtech.backend4cash.RestTemplateTests;
import ee.vovtech.backend4cash.dto.LoggedInUserDto;
import ee.vovtech.backend4cash.dto.PostDto;
import ee.vovtech.backend4cash.model.Currency;
import ee.vovtech.backend4cash.model.ForumPost;
import ee.vovtech.backend4cash.model.User;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
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
@RunWith(SpringRunner.class)
class UserControllerTest extends RestTemplateTests {


    public static final ParameterizedTypeReference<List<User>> LIST_OF_USERS =
            new ParameterizedTypeReference<>() {};
    public static final ParameterizedTypeReference<List<PostDto>> LIST_OF_POSTS =
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
    }

    @Test
    void userTestsOrderedSoHeWontSuddenlyBeDeleted() {

        getUserRequiresCorrectRole(); // admin can get all users' info, user only his own

        getUsersNeedCorrectRights(); // only admin can get all users

        postAPost();
        seeUsersPosts();
        deleteUsersPosts();
        // assert user has no posts now
        ResponseEntity<List<PostDto>> exchange = testRestTemplate.exchange("/users/" + userId + "/posts",
                HttpMethod.GET, new HttpEntity<>(authorizationHeader(userToken)), LIST_OF_POSTS);
        List<PostDto> postDtos = assertOk(exchange);
        assertEquals(0, postDtos.size());

        // user can get more cash
        userCanGetMoreCash();
        // updating user fields
        updatingUserFields();
        // user can be deleted, doing it here so other tests wont break
        userCanBeDeleted();
    }
    void getUserRequiresCorrectRole() {
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

    void getUsersNeedCorrectRights() {
        // normal users dont have access to this
        try {
            ResponseEntity<List<User>> exchangeFail = testRestTemplate.exchange("/users",
                    HttpMethod.GET, new HttpEntity<>(authorizationHeader(userToken)), LIST_OF_USERS);
        } catch (Exception e) {
            // Exception has to be thrown since user has no rights
            // response expects a JsonArray but doesn't get one, so it throws an error since it can't parse it
            assertEquals(e.getClass(), RestClientException.class);
        }
        // ensure for admin users are not null
        ResponseEntity<List<User>> exchange = testRestTemplate.exchange("/users",
                HttpMethod.GET, new HttpEntity<>(authorizationHeader(adminToken)), LIST_OF_USERS);
        List<User> users = assertOk(exchange);
        assertNotNull(users);
    }

    void updatingUserFields() {
        // you could update email, but it would require creating a new token, and if other methods work, email will work too
        // update username
        ResponseEntity<Boolean> responseUsername = testRestTemplate.exchange("/users/" + userId + "/username?username=newUsername",
                HttpMethod.PUT, new HttpEntity<>(authorizationHeader(userToken)), Boolean.class);
        assertEquals(200, responseUsername.getStatusCodeValue());
        // update status
        ResponseEntity<Boolean> responseStatus = testRestTemplate.exchange("/users/" + userId + "/status?status=newStatus",
                HttpMethod.PUT, new HttpEntity<>(authorizationHeader(userToken)), Boolean.class);
        assertEquals(200, responseStatus.getStatusCodeValue());
        // get user to see his data
        ResponseEntity<LoggedInUserDto> responseUser = testRestTemplate.exchange("/users/" + userId,
                HttpMethod.GET, new HttpEntity<>(authorizationHeader(userToken)), LoggedInUserDto.class);
        LoggedInUserDto userDto = assertOk(responseUser);
        assertEquals("newUsername", userDto.getUsername());
        assertEquals("newStatus", userDto.getStatus());
    }


    void postAPost() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", userToken);
        // maybe will need a different one
        HttpEntity<String> requestEntity = new HttpEntity<String>("test Message", headers);
        ResponseEntity<String> exchange = testRestTemplate.exchange("/posts?userId=" + userId,
                HttpMethod.POST, requestEntity, String.class);
        assertEquals(200, exchange.getStatusCodeValue());
    }

    void seeUsersPosts() {
        ResponseEntity<List<PostDto>> exchange = testRestTemplate.exchange("/users/" + userId + "/posts",
                HttpMethod.GET, new HttpEntity<>(authorizationHeader(userToken)), LIST_OF_POSTS);
        List<PostDto> postDtos = assertOk(exchange);
        assertEquals("test Message", postDtos.get(0).getMessage());
    }

    void deleteUsersPosts() {
        // Only admin can delete posts
        ResponseEntity<Void> exchange = testRestTemplate.exchange("/users/" + userId + "/posts",
                HttpMethod.DELETE, new HttpEntity<>(authorizationHeader(adminToken)), Void.class);
        assertEquals(200, exchange.getStatusCodeValue());

    }


    void userCanGetMoreCash() {
        ResponseEntity<Void> exchange = testRestTemplate.exchange("/users/" + userId + "/bablo?amount=100000",
                HttpMethod.PUT, new HttpEntity<>(authorizationHeader(userToken)), Void.class);
        assertEquals(200, exchange.getStatusCodeValue());
        // get user to see updated cash value
        ResponseEntity<LoggedInUserDto> exchangeUser = testRestTemplate.exchange("/users/" + userId,
                HttpMethod.GET, new HttpEntity<>(authorizationHeader(userToken)), LoggedInUserDto.class);
        LoggedInUserDto user = assertOk(exchangeUser);
        assertEquals(Float.parseFloat("200000"), Float.parseFloat(user.getCash()));
    }

    void userCanBeDeleted() {
        ResponseEntity<Void> exchange = testRestTemplate.exchange("/users/" + userId,
                HttpMethod.DELETE, new HttpEntity<>(authorizationHeader(adminToken)), Void.class);
        assertEquals(200, exchange.getStatusCodeValue());
        ResponseEntity<List<User>> exchangeUsers = testRestTemplate.exchange("/users",
                HttpMethod.GET, new HttpEntity<>(authorizationHeader(adminToken)), LIST_OF_USERS);
        List<User> users = assertOk(exchangeUsers);
        assertFalse(users.stream().map(User::getId).collect(Collectors.toList()).contains(userId));
    }


}