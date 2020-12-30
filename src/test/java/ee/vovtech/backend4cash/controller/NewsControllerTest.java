package ee.vovtech.backend4cash.controller;

import ee.vovtech.backend4cash.dto.NewsDto;
import ee.vovtech.backend4cash.model.News;
import ee.vovtech.backend4cash.service.user.NewsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import ee.vovtech.backend4cash.RestTemplateTests;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NewsControllerTest extends RestTemplateTests{

    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    NewsService newsService;

    private String adminToken;
    private String userToken;

    @BeforeAll
    void getTokens() {
        forumPostRepository.deleteAll();
        userRepository.deleteAll();
        adminToken = getAdminToken();
        userToken = getUserToken();
    }

    @Test
    public void saveNews(){
        NewsDto news4test = NewsDto.builder().title("meme").message("some meme").build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", adminToken);
        // maybe will need a different one
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<NewsDto> requestEntity = new HttpEntity<NewsDto>(news4test, headers);
        ResponseEntity<String> response = testRestTemplate.exchange("/news", HttpMethod.POST, requestEntity, String.class);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, newsService.findFrom(1).size());
    }

}