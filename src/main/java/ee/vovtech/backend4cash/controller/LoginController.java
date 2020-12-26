package ee.vovtech.backend4cash.controller;

import ee.vovtech.backend4cash.dto.LoginDto;
import ee.vovtech.backend4cash.dto.RegisterDto;
import ee.vovtech.backend4cash.service.user.ForumPostService;
import ee.vovtech.backend4cash.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://bits4cash.tk", "http://www.bits4cash.tk"}, maxAge = 3600)
@RestController
@RequestMapping("/users")
public class LoginController {

    @Autowired
    private UserService userService;


    // register -> create account
    @PostMapping("register")
    public ResponseEntity<Void> register(@RequestBody RegisterDto registerDto) {
        // save user if info is ok
        userService.registerUser(registerDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    // login -> generate token for frontend
    @PostMapping("login")
    public void login(@RequestBody LoginDto loginDto) {
        // generate token
    }

}
