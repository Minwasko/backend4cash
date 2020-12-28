package ee.vovtech.backend4cash.controller;

import ee.vovtech.backend4cash.dto.LoginDto;
import ee.vovtech.backend4cash.dto.LoginResponse;
import ee.vovtech.backend4cash.dto.RegisterDto;
import ee.vovtech.backend4cash.exceptions.InvalidUserException;
import ee.vovtech.backend4cash.service.user.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"https://bits4cash.tk", "https://www.bits4cash.tk", "https://frontend4cashdev"}, maxAge = 3600)
@RestController
@RequestMapping("/users")
public class LoginController {

    @Autowired
    private LoginService loginService;


    // register -> create account
    @PostMapping("register")
    public ResponseEntity<Void> register(@RequestBody RegisterDto registerDto) {
        // save user if info is ok
        if (registerDto.getEmail().isEmpty()) throw new InvalidUserException("no email set");
        if (registerDto.getUsername().isEmpty()) throw new InvalidUserException("no username set");
        if (registerDto.getPassword().isEmpty()) throw new InvalidUserException("empty password");
        loginService.registerUser(registerDto);
        // TODO FIX USER ID
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    // login -> generate token for frontend
    @PostMapping("login")
    public LoginResponse login(@RequestBody LoginDto loginDto) {
        // generate token if credentials are OK
        return loginService.login(loginDto);
    }

}
