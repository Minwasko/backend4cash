package ee.vovtech.backend4cash.service.user;

import ee.vovtech.backend4cash.exceptions.ForumPostNotFoundException;
import ee.vovtech.backend4cash.exceptions.InvalidForumPostException;
import ee.vovtech.backend4cash.model.ForumPost;
import ee.vovtech.backend4cash.model.User;
import ee.vovtech.backend4cash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ForumPostService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public ForumPost save(Long id, String message) {
        if (message.length() > 255) {
            throw new InvalidForumPostException("ForumPost message is too long");
        }
        User user = userService.findById(id);
        List<ForumPost> posts = user.getForumPosts();
        ForumPost forumPost = new ForumPost(message, user);
        posts.add(forumPost);
        user.setForumPosts(posts);
        userService.save(user);
        return forumPost;
    }

    public List<ForumPost> findAll(long id) {
        return userService.findById(id).getForumPosts();
    }

    public ForumPost findById(Long id, Long forumPostId) {
        Optional<ForumPost> forumPost = userService.findById(id).getForumPosts()
                .stream().filter(e -> e.getId() == forumPostId).findAny();
        if (forumPost.isPresent()) {
            return forumPost.get();
        } else {
            throw new ForumPostNotFoundException();
        }
    }

}
