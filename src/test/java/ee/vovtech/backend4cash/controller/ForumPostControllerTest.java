package ee.vovtech.backend4cash.controller;

import ee.vovtech.backend4cash.model.ForumPost;
import ee.vovtech.backend4cash.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ForumPostControllerTest {


    public static final ParameterizedTypeReference<List<User>> LIST_OF_USERS =
            new ParameterizedTypeReference<>() {};
    public static final ParameterizedTypeReference<List<ForumPost>> LIST_OF_POSTS =
            new ParameterizedTypeReference<>() {};
    @Autowired
    TestRestTemplate testRestTemplate;

//    @Test
//    public void postsAreNotNull() {
//        ResponseEntity<List<ForumPost>> exchange = testRestTemplate.exchange("/posts", HttpMethod.GET, null, LIST_OF_POSTS);
//        assertNotNull(exchange.getBody());
//    }
//
//    @Test
//    public void getPostById() {
//        ResponseEntity<List<ForumPost>> exchange = testRestTemplate.exchange("/posts", HttpMethod.GET, null, LIST_OF_POSTS);
//        assertNotNull(exchange.getBody());
//        ForumPost dbPost = exchange.getBody().get(0);
//        ResponseEntity<ForumPost> exchangePost = testRestTemplate.exchange("/posts/" + dbPost.getId(), HttpMethod.GET, null, ForumPost.class);
//        assertNotNull(exchangePost.getBody());
//        assertEquals(dbPost.getMessage(), exchangePost.getBody().getMessage());
//    }
//
//    @Test
//    public void saveNewPost() {
//        ResponseEntity<List<User>> exchange = testRestTemplate.exchange("/users", HttpMethod.GET, null, LIST_OF_USERS);
//        assertNotNull(exchange.getBody());
//        User dbUser = exchange.getBody().get(0);
//        testRestTemplate.exchange("/posts?userId=" + dbUser.getId() , HttpMethod.POST, new HttpEntity<>("testForumPostMessage"), ForumPost.class);
//        ResponseEntity<List<ForumPost>> exchangePosts = testRestTemplate.exchange("/posts", HttpMethod.GET, null, LIST_OF_POSTS);
//        assertNotNull(exchangePosts.getBody());
//        List<String> messages = exchangePosts.getBody().stream().map(ForumPost::getMessage).collect(Collectors.toList());
//        assertTrue(messages.contains("testForumPostMessage"));
//    }
//
//    @Test
//    public void deletePost() {
//        ResponseEntity<List<ForumPost>> exchange = testRestTemplate.exchange("/posts", HttpMethod.GET, null, LIST_OF_POSTS);
//        assertNotNull(exchange.getBody());
//        ForumPost dbPost = exchange.getBody().get(0);
//        String oldMessage = dbPost.getMessage();
//        testRestTemplate.exchange("/posts/" + dbPost.getId(), HttpMethod.DELETE, null, ForumPost.class);
//        ResponseEntity<List<ForumPost>> exchangePosts = testRestTemplate.exchange("/posts", HttpMethod.GET, null, LIST_OF_POSTS);
//        assertNotNull(exchangePosts.getBody());
//        List<String> messages = exchangePosts.getBody().stream().map(ForumPost::getMessage).collect(Collectors.toList());
//        assertFalse(messages.contains(oldMessage));
//
//    }

}