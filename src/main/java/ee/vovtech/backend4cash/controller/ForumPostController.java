package ee.vovtech.backend4cash.controller;


import ee.vovtech.backend4cash.dto.NewForumPostDto;
import ee.vovtech.backend4cash.dto.PostDto;
import ee.vovtech.backend4cash.security.Roles;
import ee.vovtech.backend4cash.service.user.ForumPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class ForumPostController {

    @Autowired
    private ForumPostService forumPostService;

    @Secured({Roles.ADMIN, Roles.USER})
    @PostMapping
    public void saveForumPost(@RequestBody NewForumPostDto forumPostDto) {
        forumPostService.save(forumPostDto);
    }

    @GetMapping("{id}")
    public PostDto getForumPost(@PathVariable("id") long id) {
        return forumPostService.findById(id);
    }

    @GetMapping
    public List<PostDto> getPostsAmount(@RequestParam long amount){
        return forumPostService.findAmount(amount);
    }

    @Secured(Roles.ADMIN)
    @DeleteMapping("{id}")
    public void deleteForumPost(@PathVariable("id") long id) {
        forumPostService.deleteForumPost(id);
    }
}
