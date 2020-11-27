package ee.vovtech.backend4cash.service.user;

import ee.vovtech.backend4cash.exceptions.InvalidForumPostException;
import ee.vovtech.backend4cash.exceptions.UserNotFoundException;
import ee.vovtech.backend4cash.model.ForumPost;
import ee.vovtech.backend4cash.model.User;
import ee.vovtech.backend4cash.repository.ForumPostRepository;
import ee.vovtech.backend4cash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForumPostService {

    @Autowired
    private UserService userService;

    @Autowired
    private ForumPostRepository forumPostRepository;

    public ForumPost save(Long id, String message) {
        if (message.length() > 255) {
            throw new InvalidForumPostException("ForumPost message is too long");
        }
        User user = userService.findById(id);
        ForumPost forumPost = new ForumPost(message, user);
        return forumPostRepository.save(forumPost);
    }

    public List<ForumPost> findAll() {
        return forumPostRepository.findAll();
    }

    public ForumPost findById(long id) {
        if (forumPostRepository.findById(id).isPresent()) return forumPostRepository.findById(id).get();
        throw new UserNotFoundException();
    }

    public void deleteForumPost(long id) {
        forumPostRepository.delete(findById(id));
    }

    public List<ForumPost> findAllPostsByUserId(long id) {
        return findAll().stream().filter(post -> post.getUser().getId() == id).collect(Collectors.toList());
    }

    public void deleteAllPostsFromUser(long id) {
        List<ForumPost> posts = findAll().stream().filter(e -> e.getUser().getId() == id).collect(Collectors.toList());
        posts.forEach(post -> deleteForumPost(post.getId()));
    }

}
