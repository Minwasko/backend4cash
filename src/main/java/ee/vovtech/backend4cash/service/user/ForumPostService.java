package ee.vovtech.backend4cash.service.user;

import ee.vovtech.backend4cash.dto.PostDto;
import ee.vovtech.backend4cash.exceptions.InvalidForumPostException;
import ee.vovtech.backend4cash.exceptions.UserNotFoundException;
import ee.vovtech.backend4cash.model.ForumPost;
import ee.vovtech.backend4cash.model.User;
import ee.vovtech.backend4cash.repository.ForumPostRepository;
import ee.vovtech.backend4cash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForumPostService {

    @Autowired
    private UserService userService;

    @Autowired
    private ForumPostRepository forumPostRepository;

    public void save(Long id, String message) {
        if (message.length() > 255) {
            throw new InvalidForumPostException("ForumPost message is too long");
        }
        User user = userService.findById(id);
        ForumPost forumPost = new ForumPost(message, user);
        forumPostRepository.save(forumPost);
    }

    @Deprecated // fk this
    public List<PostDto> findAll() {
        List<ForumPost> dbPosts = forumPostRepository.findAll();
        List<PostDto> postDtos = new ArrayList<>();
        for (ForumPost dbPost : dbPosts) {
            postDtos.add(PostDto.builder().username(dbPost.getUser().getUsername()).message(dbPost.getMessage()).build());
        }
        return postDtos;
    }

    // we use this
    public List<PostDto> findFrom(long id){
        
        List<PostDto> toReturn = new ArrayList<>();
        for (long i = id; i <= id + 5; i++){
            if(forumPostRepository.existsById(i)){
                toReturn.add(createPostDto(forumPostRepository.findById(i).get()));
            }
        }

        return toReturn;
    }

    private PostDto createPostDto(ForumPost post){

        return PostDto.builder()
                .id(post.getId())
                .username(post.getUser().getUsername())
                .message(post.getMessage())
                .build();
    }

    public PostDto findById(long id) {
        if (forumPostRepository.findById(id).isPresent()) {
            ForumPost dbPost = forumPostRepository.findById(id).get();
            return PostDto.builder().username(dbPost.getUser().getUsername()).message(dbPost.getMessage()).build();
        }
        throw new UserNotFoundException();
    }

    public void deleteForumPost(long id) {
        if (forumPostRepository.findById(id).isPresent()) forumPostRepository.delete(forumPostRepository.findById(id).get());
    }

    public List<ForumPost> findAllPostsByUserId(long id) {
        return forumPostRepository.findAll().stream().filter(post -> post.getUser().getId() == id).collect(Collectors.toList());
    }

    public void deleteAllPostsFromUser(long id) {
        List<ForumPost> posts = forumPostRepository.findAll().stream().filter(e -> e.getUser().getId() == id).collect(Collectors.toList());
        posts.forEach(post -> deleteForumPost(post.getId()));
    }

    

}
