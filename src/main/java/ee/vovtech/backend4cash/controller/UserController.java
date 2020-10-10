package ee.vovtech.backend4cash.controller;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ee.vovtech.backend4cash.model.ForumPost;
import ee.vovtech.backend4cash.model.User;
import ee.vovtech.backend4cash.service.user.ForumPostService;
import ee.vovtech.backend4cash.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/users")
@CrossOrigin(origins = {"http://localhost:4003", "http://localhost:4200"}, maxAge = 3600)

public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ForumPostService forumPostService;

    @PostMapping
    public User saveUser(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping("{id}")
    public User getUser(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.findAll();
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("{id}/posts")
    // See users forum posts
    public List<ForumPost> getUserForumPosts(@PathVariable Long id) {
        return forumPostService.findAll(id);
    }


}