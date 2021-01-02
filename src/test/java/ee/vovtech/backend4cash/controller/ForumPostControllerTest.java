package ee.vovtech.backend4cash.controller;

import ee.vovtech.backend4cash.RestTemplateTests;
import ee.vovtech.backend4cash.dto.NewForumPostDto;
import ee.vovtech.backend4cash.dto.PostDto;
import ee.vovtech.backend4cash.model.User;
import ee.vovtech.backend4cash.service.user.NewsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.yaml")
@RunWith(SpringRunner.class)
class ForumPostControllerTest extends RestTemplateTests {

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
    private long postId;

    @BeforeAll
    void getTokens() {
        forumPostRepository.deleteAll();
        userRepository.deleteAll();
        adminToken = getAdminToken();
        userToken = getUserToken();
        adminId = userRepository.findAll().get(0).getId();
        userId = userRepository.findAll().get(1).getId();
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
        NewForumPostDto forumPostDto = NewForumPostDto.builder().authorEmail("user@user.user")
                .content("test message").title("test title").build();
        HttpEntity<NewForumPostDto> requestEntity = new HttpEntity<>(forumPostDto, headers);
        ResponseEntity<NewForumPostDto> exchange = testRestTemplate.exchange("/posts",
                HttpMethod.POST, requestEntity, NewForumPostDto.class);
        assertEquals(200, exchange.getStatusCodeValue());
    }

    void getPostsIsNotNull() {
        ResponseEntity<List<PostDto>> exchange = testRestTemplate.exchange("/posts?amount=10",
                HttpMethod.GET, null, LIST_OF_POSTS);
        List<PostDto> postDtos = assertOk(exchange);
        assertNotNull(postDtos);
        postId = forumPostRepository.findAll().get(0).getId();
    }

    void deletePost() {
        // check that post exists
        ResponseEntity<PostDto> exchangePost = testRestTemplate.exchange("/posts/" + postId,
                HttpMethod.GET, null, PostDto.class);
        PostDto postDto = assertOk(exchangePost);
        // delete that post
        assertEquals("test message", postDto.getContent());
        assertEquals("test title", postDto.getTitle());
        ResponseEntity<PostDto> exchange = testRestTemplate.exchange("/posts/" + postId,
                HttpMethod.DELETE, new HttpEntity<>(authorizationHeader(adminToken)), PostDto.class);
        assertEquals(200, exchange.getStatusCodeValue());
        // check that db has no posts
        ResponseEntity<List<PostDto>> exchangePosts = testRestTemplate.exchange("/posts?amount=10",
                HttpMethod.GET, null, LIST_OF_POSTS);
        List<PostDto> postDtos = assertOk(exchangePosts);
        assertEquals(0, postDtos.size());
    }


}