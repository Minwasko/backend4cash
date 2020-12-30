package ee.vovtech.backend4cash.controller;


import ee.vovtech.backend4cash.dto.LoggedInUserDto;
import ee.vovtech.backend4cash.model.ForumPost;
import ee.vovtech.backend4cash.model.User;
import ee.vovtech.backend4cash.security.Roles;
import ee.vovtech.backend4cash.service.currency.CurrencyPriceService;
import ee.vovtech.backend4cash.service.user.ForumPostService;
import ee.vovtech.backend4cash.service.user.LoginService;
import ee.vovtech.backend4cash.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = {"https://bits4cash.tk", "https://www.bits4cash.tk", "https://frontend4cashdev"}, maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ForumPostService forumPostService;
    @Autowired
    private LoginService loginService;

    @Secured({Roles.ADMIN, Roles.USER})
    @GetMapping("{id}")
    public LoggedInUserDto getUser(@PathVariable Long id, @RequestHeader(name="Authorization") String token) {
        return loginService.getUserByIdWithTokenCheck(id, token);
    }

    @Secured(Roles.ADMIN)
    @GetMapping
    public List<User> getUsers(@RequestParam(required = false) String nickName, @RequestParam(required = false) String email) {
        if (nickName != null) return userService.findByNickname(nickName);
        else if (email != null) return List.of(userService.findByEmail(email));
        return userService.findAll();
    }

    @Secured({Roles.USER, Roles.ADMIN})
    @PutMapping("{id}/email") // email, nickname, password, status
    public boolean updateUserEmail(@PathVariable Long id, @RequestParam String email) {
        return userService.update(id, "email", email);
    }

    @Secured({Roles.USER, Roles.ADMIN})
    @PutMapping("{id}/nickname")
    public boolean updateUserNickname(@PathVariable Long id, @RequestParam String nickname) {
        return userService.update(id, "nickname", nickname);
    }

    @Secured({Roles.USER, Roles.ADMIN})
    @PutMapping("{id}/password")
    public boolean updateUserPassword(@PathVariable Long id, @RequestParam String password) {
        return userService.update(id, "password", password);
    }

    @Secured({Roles.USER, Roles.ADMIN})
    @PutMapping("{id}/status")
    public boolean updateUserStatus(@PathVariable Long id, @RequestParam String status) {
        return userService.update(id, "status", status);
    }

    @Secured(Roles.ADMIN)
    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("{id}/posts")
    public List<ForumPost> getUserForumPosts(@PathVariable Long id) {
        return forumPostService.findAllPostsByUserId(id);
    }

    @Secured(Roles.ADMIN)
    @DeleteMapping("{id}/posts")
    public void deleteAllUserForumPosts(@PathVariable Long id) {
        forumPostService.deleteAllPostsFromUser(id);
    }

    @Secured({Roles.ADMIN, Roles.USER})
    @PutMapping("{id}/bablo")
    public void addCash(@PathVariable Long id, @RequestParam Long amount){
        userService.addCash(id, amount);
    }

}