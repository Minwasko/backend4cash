package ee.vovtech.backend4cash.controller;


import ee.vovtech.backend4cash.model.ForumPost;
import ee.vovtech.backend4cash.model.User;
import ee.vovtech.backend4cash.security.Roles;
import ee.vovtech.backend4cash.service.currency.CurrencyPriceService;
import ee.vovtech.backend4cash.service.user.ForumPostService;
import ee.vovtech.backend4cash.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"https://bits4cash.tk", "https://www.bits4cash.tk"}, maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ForumPostService forumPostService;

    @Secured(Roles.ADMIN)
    @GetMapping("{id}")
    public User getUser(@PathVariable Long id) {
        return userService.findById(id);
    }

    @Secured(Roles.ADMIN)
    @GetMapping
    public List<User> getUsers(@RequestParam(required = false) String nickName, @RequestParam(required = false) String email) {
        if (nickName != null) return userService.findByNickname(nickName);
        else if (email != null) return userService.findByEmail(email);
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

}