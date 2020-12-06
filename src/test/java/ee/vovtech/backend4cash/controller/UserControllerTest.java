package ee.vovtech.backend4cash.controller;

import ee.vovtech.backend4cash.model.Currency;
import ee.vovtech.backend4cash.model.ForumPost;
import ee.vovtech.backend4cash.model.User;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
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
class UserControllerTest {


    public static final ParameterizedTypeReference<List<User>> LIST_OF_USERS =
            new ParameterizedTypeReference<>() {};
    public static final ParameterizedTypeReference<List<ForumPost>> LIST_OF_POSTS =
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
        User user = new User((long) 999, "testUser", "yo@yo,com", "12345lol");
        ResponseEntity<User> exchange = testRestTemplate.exchange("/users", HttpMethod.POST, new HttpEntity<>(user), User.class);
        User dbUser = exchange.getBody();
        assertNotNull(dbUser);
        assertEquals("testUser", dbUser.getNickname());
        TEST_USER_ID = dbUser.getId();
    }
    // TODO: fix update test
//
//    @Test
//    void updateUser() {
//        ResponseEntity<User> exchange = testRestTemplate.exchange("/users/" + TEST_USER_ID , HttpMethod.GET, null, User.class);
//        User dbUser = exchange.getBody();
//        assertNotNull(dbUser);
//        dbUser.setNickname("testUserUpdated");
//        ResponseEntity<User> exchangeUpdated = testRestTemplate.exchange("/users/" + dbUser.getId(), HttpMethod.PUT, new HttpEntity<>(dbUser), User.class);
//        User dbUserUpdated = exchangeUpdated.getBody();
//        assertNotNull(dbUserUpdated);
//        assertEquals("testUserUpdated", dbUserUpdated.getNickname());
//        TEST_USER_ID = dbUser.getId();
//    }

    @Test
    void deleteUser() {
        ResponseEntity<User> exchange = testRestTemplate.exchange("/users/" + TEST_USER_ID , HttpMethod.GET, null, User.class);
        User dbUser = exchange.getBody();
        assertNotNull(dbUser);
        testRestTemplate.exchange("/users/" + dbUser.getId(), HttpMethod.DELETE, new HttpEntity<>(dbUser), Currency.class);
        ResponseEntity<List<User>> exchangeUsers = testRestTemplate.exchange("/users", HttpMethod.GET, null, LIST_OF_USERS);
        assertNotNull(exchangeUsers.getBody());
        assertFalse(exchangeUsers.getBody().contains(dbUser));
    }

    @Test
    void deleteUserForumPosts() {
        ResponseEntity<List<User>> exchange = testRestTemplate.exchange("/users", HttpMethod.GET, null, LIST_OF_USERS);
        assertNotNull(exchange.getBody());
        User dbUser = exchange.getBody().get(0);
        testRestTemplate.exchange("/users/" + dbUser.getId() + "/posts", HttpMethod.DELETE, null, User.class);
        // old version with user having link to posts
//        ResponseEntity<List<User>> exchangeAfterDelete = testRestTemplate.exchange("/users", HttpMethod.GET, null, LIST_OF_USERS);
//        assertNotNull(exchangeAfterDelete.getBody());
//        User userAfterDeletion = exchangeAfterDelete.getBody().get(0);
        // new version
        ResponseEntity<List<ForumPost>> exchangeAfterDelete = testRestTemplate.exchange("/users/" + dbUser.getId() + "/posts", HttpMethod.GET, null, LIST_OF_POSTS);
        assertNotNull(exchangeAfterDelete.getBody());
        List<ForumPost> postsAfterDelete = exchangeAfterDelete.getBody();
        assertEquals(new ArrayList<>(), postsAfterDelete);
    }

    @Test
    void getUserForumPosts() {
        ResponseEntity<List<User>> exchange = testRestTemplate.exchange("/users", HttpMethod.GET, null, LIST_OF_USERS);
        assertNotNull(exchange.getBody());
        User dbUser = exchange.getBody().get(0);
        testRestTemplate.exchange("/posts?userId=" + dbUser.getId() , HttpMethod.POST, new HttpEntity<>("testMessage"), ForumPost.class);
        ResponseEntity<List<ForumPost>> exchangeUserPosts = testRestTemplate.exchange("/users/" + dbUser.getId() + "/posts" , HttpMethod.GET, null, LIST_OF_POSTS);
        assertNotNull(exchangeUserPosts);
        List<ForumPost> posts = exchangeUserPosts.getBody();
        assertNotNull(posts);
        assertEquals("testMessage", posts.get(0).getMessage());
        assertEquals(dbUser.getNickname(), posts.get(0).getUser().getNickname());
    }

}