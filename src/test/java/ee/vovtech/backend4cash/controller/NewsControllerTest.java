package ee.vovtech.backend4cash.controller;

import ee.vovtech.backend4cash.dto.NewsDto;
import ee.vovtech.backend4cash.model.User;
import ee.vovtech.backend4cash.repository.NewsRepository;
import ee.vovtech.backend4cash.service.user.NewsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import ee.vovtech.backend4cash.RestTemplateTests;
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
class NewsControllerTest extends RestTemplateTests{

    public static final ParameterizedTypeReference<List<NewsDto>> LIST_OF_NEWS =
            new ParameterizedTypeReference<>() {};

    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    NewsService newsService;
    @Autowired
    NewsRepository newsRepository;

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
    public void saveNews(){
        NewsDto news4test = NewsDto.builder().title("meme").message("some meme").build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", adminToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<NewsDto> requestEntity = new HttpEntity<>(news4test, headers);
        ResponseEntity<String> response = testRestTemplate.exchange("/news", HttpMethod.POST, requestEntity, String.class);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, newsService.findFromNewest(1).size());
    }

    @Test
    public void getNewsFromNewest(){
        newsRepository.deleteAll();
        postSome(10);
        ResponseEntity<List<NewsDto>> response
                = testRestTemplate.exchange("/news/?amount=4", HttpMethod.GET, null, LIST_OF_NEWS);
        List<NewsDto> news = assertOk(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(4, news.size());
        assertEquals("meme 7", news.get(news.size() - 1).getTitle());

    }

    @Test
    public void getNewsFromNewest2(){
        newsRepository.deleteAll();
        postSome(5);
        ResponseEntity<List<NewsDto>> response
                = testRestTemplate.exchange("/news/?amount=10", HttpMethod.GET, null, LIST_OF_NEWS);
        List<NewsDto> news = assertOk(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(5, news.size());
        assertEquals("meme 1", news.get(news.size() - 1).getTitle());

    }

    //find amount 50 but get 10 posted

    private void postSome(int amount){

        for (int i = 1; i <= amount; i++){
            newsService.save(NewsDto.builder().title("meme " + i).message("sth").build());

        }
    }

}