package ee.vovtech.backend4cash.controller;

import ee.vovtech.backend4cash.RestTemplateTests;
import ee.vovtech.backend4cash.dto.LoginDto;
import ee.vovtech.backend4cash.dto.NewsDto;
import ee.vovtech.backend4cash.dto.RegisterDto;
import ee.vovtech.backend4cash.model.User;
import ee.vovtech.backend4cash.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.yaml")
@RunWith(SpringRunner.class)
class LoginControllerTest extends RestTemplateTests {

    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    UserService userService;


    @Test
    public void registerTest(){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<RegisterDto> requestEntity = new HttpEntity<>(buildADude(), headers);
        ResponseEntity<String> response = testRestTemplate.exchange("/users/register", HttpMethod.POST, requestEntity, String.class);
        assertEquals(200, response.getStatusCodeValue());
        System.out.println(userService.findAll().stream().map(User::getUsername).collect(Collectors.toList()));
        assertNotNull(userService.findByEmail("aufer322@some.org"));

    }

    @Test
    public void loginTest(){

    }

    private RegisterDto buildADude(){
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("aufer322@some.org");
        registerDto.setUsername("Van");
        registerDto.setPassword("12345lol");
        return registerDto;
    }

}