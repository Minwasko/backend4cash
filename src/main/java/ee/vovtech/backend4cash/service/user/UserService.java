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

    public User save(User user) {
        return userRepository.save(user);
    }

    public boolean update(Long id, String infoToChange, String value) {
        User dbUser = findById(id);
        switch (infoToChange) {
            case "email":
                if (findAll().stream().noneMatch(user -> user.getEmail().equals(value))) {
                    dbUser.setEmail(value);
                }
                else return false;
                break;
            case "nickname":
                if (findAll().stream().noneMatch(user -> user.getNickname().equals(value))) dbUser.setNickname(value);
                else return false;
                break;
            case "password":
                dbUser.setPassword(value);
                break;
            case "status":
                dbUser.setStatus(value);
                break;
        }
        save(dbUser);
        return true;
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

    // !!! temporary solution for id thingies in users. Has to be fixed l8r
    public boolean idIsTaken(long userID){
        return userRepository.existsById(userID);
    }

}
