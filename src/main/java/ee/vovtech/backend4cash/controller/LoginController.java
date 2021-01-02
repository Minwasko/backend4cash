package ee.vovtech.backend4cash.controller;

import ee.vovtech.backend4cash.dto.LoginDto;
import ee.vovtech.backend4cash.dto.LoginResponse;
import ee.vovtech.backend4cash.dto.RegisterDto;
import ee.vovtech.backend4cash.dto.RegisterResponse;
import ee.vovtech.backend4cash.exceptions.InvalidUserException;
import ee.vovtech.backend4cash.service.user.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class LoginController {

    @Autowired
    private LoginService loginService;


    @PostMapping("register")
    public RegisterResponse register(@RequestBody RegisterDto registerDto) {
        if (registerDto.getEmail().isEmpty()) throw new InvalidUserException("no email set");
        if (registerDto.getUsername().isEmpty()) throw new InvalidUserException("no username set");
        if (registerDto.getPassword().isEmpty()) throw new InvalidUserException("empty password");
        loginService.registerUser(registerDto);
        return RegisterResponse.builder().success(true).build();

    }

    @PostMapping("login")
    public LoginResponse login(@RequestBody LoginDto loginDto) {
        // generate token if credentials are OK
        return loginService.login(loginDto);
    }

}
