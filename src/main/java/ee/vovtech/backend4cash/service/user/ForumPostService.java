package ee.vovtech.backend4cash.service.user;

import ee.vovtech.backend4cash.exceptions.InvalidForumPostException;
import ee.vovtech.backend4cash.model.ForumPost;
import ee.vovtech.backend4cash.model.User;
import ee.vovtech.backend4cash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        ForumPost forumPost = new ForumPost(message, user);
        user.addForumPost(forumPost);
        userService.save(user);
        return forumPost;
    }

    public List<ForumPost> findAll(long id) {
        return userService.findById(id).getForumPosts();
    }

}
