package ee.vovtech.backend4cash.controller;


import ee.vovtech.backend4cash.model.ForumPost;
import ee.vovtech.backend4cash.service.user.ForumPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"https://bits4cash.tk", "https://www.bits4cash.tk"}, maxAge = 3600)
@RestController
@RequestMapping("/posts")
public class ForumPostController {

    @Autowired
    private ForumPostService forumPostService;

    @PostMapping
    // to post a forum post. Takes message and user id from the frontend and is passed to forumpostservice
    // be saved in the dee bee
    public ForumPost saveForumPost(@RequestBody String message, @RequestParam long userId) {
        return forumPostService.save(userId, message);
    }

    @GetMapping("{id}")
    public ForumPost getForumPost(@PathVariable("id") long id) {
        return forumPostService.findById(id);
    }

    @GetMapping
    public List<ForumPost> getAllForumPosts(){
        return forumPostService.findAll();
    }

    @DeleteMapping("{id}")
    public void deleteForumPost(@PathVariable("id") long id) {
        forumPostService.deleteForumPost(id);
    }




}
