package ee.vovtech.backend4cash.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity @Getter @Setter @NoArgsConstructor
public class ForumPost {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String message;
    private String type; // news or post

    @ManyToOne
    @JoinColumn(name = "FK_UserId")
    private User user;

    public ForumPost(String message, User user) {
        this.user = user;
        this.message = message;
    }
}
