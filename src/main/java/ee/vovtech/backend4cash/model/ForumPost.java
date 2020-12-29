package ee.vovtech.backend4cash.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity @Getter @Setter @NoArgsConstructor
public class ForumPost {

    @Id
    @GeneratedValue(generator = "post_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "post_id_seq", sequenceName = "post_id_seq", allocationSize = 50)
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
