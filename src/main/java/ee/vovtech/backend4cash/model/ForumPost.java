package ee.vovtech.backend4cash.model;

import javax.persistence.*;

@Entity
public class ForumPost {
    @Id
    @GeneratedValue
    private long id;

    private String message;

    @ManyToOne
    @JoinColumn(name = "FK_UserId")
    private User user;

    public ForumPost() {
    }

    public ForumPost(String message, User user) {
        this.message = message;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
