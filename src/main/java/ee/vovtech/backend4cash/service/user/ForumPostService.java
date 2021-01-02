package ee.vovtech.backend4cash.service.user;

import ee.vovtech.backend4cash.dto.PostDto;
import ee.vovtech.backend4cash.exceptions.InvalidForumPostException;
import ee.vovtech.backend4cash.exceptions.UserNotFoundException;
import ee.vovtech.backend4cash.model.ForumPost;
import ee.vovtech.backend4cash.model.User;
import ee.vovtech.backend4cash.repository.ForumPostRepository;
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

    public List<PostDto> findAmount(long amount){

        List<PostDto> toReturn = new ArrayList<>();
        ForumPost lastPost = null;
        if (forumPostRepository.findAll().size() > 0) {
            lastPost = forumPostRepository.findAll().get(forumPostRepository.findAll().size() - 1);
        }
        if (lastPost != null) {
            long lastPostId = lastPost.getId();
            for (long i = lastPostId; i > lastPostId - amount; i--){
                if(forumPostRepository.findById(i).isPresent()){
                    toReturn.add(createPostDto(forumPostRepository.findById(i).get()));
                }
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
        if (forumPostRepository.findById(id).isPresent()){
            forumPostRepository.delete(forumPostRepository.findById(id).get());
        }
    }

    public List<PostDto> findAllPostsByUserId(long id) {
        List<ForumPost> userPosts = forumPostRepository.findAll().stream().filter(post -> post.getUser().getId() == id)
                .collect(Collectors.toList());
        List<PostDto> postDtos = new ArrayList<>();
        for(ForumPost post : userPosts) {
            postDtos.add(PostDto.builder().username(post.getUser().getUsername())
                    .message(post.getMessage()).id(post.getId()).build());
        }
        return postDtos;
    }

    public void deleteAllPostsFromUser(long id) {
        List<ForumPost> posts = forumPostRepository.findAll().stream()
                .filter(e -> e.getUser().getId() == id).collect(Collectors.toList());
        posts.forEach(post -> deleteForumPost(post.getId()));
    }

    

}
