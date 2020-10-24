package ee.vovtech.backend4cash.controller;


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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4003", "http://localhost:4200"}, maxAge = 3600)
@RestController
@RequestMapping("/users")
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

    @GetMapping("check/{id}")
    public boolean idIsTaken(@PathVariable Long id){
        return userService.idIsTaken(id);
    }

    @PutMapping("{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.update(id, user);
    }

    @GetMapping
    public List<User> getUsers(@RequestParam(required = false) String nickName, @RequestParam(required = false) String email) {
        if (nickName != null) {
            return userService.findByNickname(nickName);
        } else if (email != null) {
            return userService.findByEmail(email);
        }
        return userService.findAll();
    }


    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("{id}/posts")
    // See users forum posts
    public List<ForumPost> getUserForumPosts(@PathVariable Long id) {
        return userService.findPostsByUser(id);
    }

    @DeleteMapping("{id}/posts")
    // See users forum posts
    public void deleteAllUserForumPosts(@PathVariable Long id) {
        userService.deleteAllPosts(id);
    }


}