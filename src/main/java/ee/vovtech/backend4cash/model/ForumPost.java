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




}
