package ee.vovtech.backend4cash.service.user;

import ee.vovtech.backend4cash.exceptions.InvalidUserException;
import ee.vovtech.backend4cash.exceptions.UserNotFoundException;
import ee.vovtech.backend4cash.model.User;
import ee.vovtech.backend4cash.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;


import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void save(User user) {
        if (userRepository.findByEmail(user.getEmail()) == null) {
            userRepository.save(user);
        } else {
            throw new InvalidUserException("Email already in use");
        }
    }

    public void updateUser(User user) {
        userRepository.save(user);
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
            case "username":
                dbUser.setUsername(value);
                break;
            case "status":
                dbUser.setStatus(value);
                break;
        }
        updateUser(dbUser);
        return true;
    }

    public User findById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            return userRepository.findById(id).get();
        }
        throw new UserNotFoundException();
    }

    public List<User> findByUsername(String username) {
        return findAll().stream().filter(user -> user.getUsername().equals(username)).collect(Collectors.toList());
    }

    public User findByEmail(String email) {
        return findAll().stream().filter(user -> user.getEmail().equals(email)).findAny().orElse(null);
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

    public void addCash(Long id, Long amount){
        log.info("Tryna add " + amount + " of cash to " + id + " account...");
        updateUser(findById(id).addCash(amount));
    }

}
