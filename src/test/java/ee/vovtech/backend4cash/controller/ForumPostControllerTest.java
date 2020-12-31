package ee.vovtech.backend4cash.controller;

import ee.vovtech.backend4cash.RestTemplateTests;
import ee.vovtech.backend4cash.dto.NewsDto;
import ee.vovtech.backend4cash.dto.PostDto;
import ee.vovtech.backend4cash.model.ForumPost;
import ee.vovtech.backend4cash.model.User;
import ee.vovtech.backend4cash.service.user.NewsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.yaml")
class ForumPostControllerTest extends RestTemplateTests {


    public static final ParameterizedTypeReference<List<User>> LIST_OF_USERS =
            new ParameterizedTypeReference<>() {};
    public static final ParameterizedTypeReference<List<PostDto>> LIST_OF_POSTS =
            new ParameterizedTypeReference<>() {};

    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    NewsService newsService;

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
        // admin - 8
        // user - 9
    }

    @Test
    void postsCanBeSavedAndDeleted() {
        savePost();
        getPostsIsNotNull();
        deletePost();
    }

    void savePost() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", adminToken);
        // maybe will need a different one
        HttpEntity<String> requestEntity = new HttpEntity<String>("test Message", headers);
        ResponseEntity<String> exchange = testRestTemplate.exchange("/posts?userId=" + adminId,
                HttpMethod.POST, requestEntity, String.class);
        assertEquals(200, exchange.getStatusCodeValue());
    }

    void getPostsIsNotNull() {
        ResponseEntity<List<PostDto>> exchange = testRestTemplate.exchange("/posts?amount=10",
                HttpMethod.GET, null, LIST_OF_POSTS);
        List<PostDto> postDtos = assertOk(exchange);
        assertNotNull(postDtos);
    }

    void deletePost() {
        // check that post exists
        ResponseEntity<PostDto> exchangePost = testRestTemplate.exchange("/posts/1",
                HttpMethod.GET, null, PostDto.class);
        PostDto postDto = assertOk(exchangePost);
        // delete that post
        assertEquals("test Message", postDto.getMessage());
        ResponseEntity<PostDto> exchange = testRestTemplate.exchange("/posts/1",
                HttpMethod.DELETE, new HttpEntity<>(authorizationHeader(adminToken)), PostDto.class);
        assertEquals(200, exchange.getStatusCodeValue());
        // check that db has no posts
        ResponseEntity<List<PostDto>> exchangePosts = testRestTemplate.exchange("/posts?amount=10",
                HttpMethod.GET, null, LIST_OF_POSTS);
        List<PostDto> postDtos = assertOk(exchangePosts);
        assertEquals(0, postDtos.size());
    }


}