package ee.vovtech.backend4cash.service.user;

import ee.vovtech.backend4cash.exceptions.InvalidUserException;
import ee.vovtech.backend4cash.exceptions.UserNotFoundException;
import ee.vovtech.backend4cash.model.ForumPost;
import ee.vovtech.backend4cash.model.User;
import ee.vovtech.backend4cash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }


    public User findById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            return userRepository.findById(id).get();
        }
        throw new UserNotFoundException();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void delete(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.delete(userRepository.findById(id).get());
        } else {
            throw new UserNotFoundException();
        }
    }

    public List<ForumPost> findPostsByUser(long userId){
        return findById(userId).getForumPosts();
    }

    public void deletePost(long userId, long postId){
        User user = findById(userId);
        List<ForumPost> forumPosts = user.getForumPosts();
        ForumPost forumPost = forumPosts.stream().filter(post -> post.getId() == postId).findFirst().get();
        forumPost.setUser(null);
        forumPosts.remove(forumPost);
        user.setForumPosts(forumPosts);
        save(user);
    }

}
