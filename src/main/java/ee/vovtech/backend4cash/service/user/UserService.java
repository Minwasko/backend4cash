package ee.vovtech.backend4cash.service.user;

import ee.vovtech.backend4cash.exceptions.UserNotFoundException;
import ee.vovtech.backend4cash.model.ForumPost;
import ee.vovtech.backend4cash.model.User;
import ee.vovtech.backend4cash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ForumPostService forumPostService;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User update(Long id, User user) {
        User dbUser = findById(id);
        dbUser.setNickname(user.getNickname());
        save(dbUser);
        return dbUser;
    }


    public User findById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            return userRepository.findById(id).get();
        }
        throw new UserNotFoundException();
    }

    public List<User> findByNickname(String nickName) {
        return findAll().stream().filter(user -> user.getNickname().equals(nickName)).collect(Collectors.toList());
    }

    public List<User> findByEmail(String email) {
        return findAll().stream().filter(user -> user.getEmail().equals(email)).collect(Collectors.toList());
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

    // !!! temporary solution for id thingies in users. Has to be fixed l8r
    public boolean idIsTaken(long userID){
        return userRepository.existsById(userID);
    }



    //TODO refactor so we dont pass userID here + maybe rename method to deletePostFromUser cause
    // its what it does
    public void deletePost(long userId, long postId){
        User user = findById(userId);
        List<ForumPost> forumPosts = user.getForumPosts();
        ForumPost forumPost = forumPosts.stream().filter(post -> post.getId() == postId).findFirst().get();
        forumPost.setUser(null);
        forumPosts.remove(forumPost);
        user.setForumPosts(forumPosts);
        save(user);
    }

    public void deleteAllPosts(long userId) {
        User user = findById(userId);
        List<ForumPost> posts = user.getForumPosts();
        for (ForumPost post : posts) {
            post.setUser(null);
            forumPostService.deleteForumPost(post.getId());
        }
        posts.clear();
        user.setForumPosts(posts);
        save(user);
    }
}
