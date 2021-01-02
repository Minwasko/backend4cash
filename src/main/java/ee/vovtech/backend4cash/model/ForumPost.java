package ee.vovtech.backend4cash.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity @Getter @Setter @NoArgsConstructor
public class ForumPost {

    @Id
    @GeneratedValue(generator = "forum_post_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "forum_post_id_seq", sequenceName = "forum_post_id_seq", allocationSize = 50)
    private long id;


    @Column(columnDefinition = "TEXT")
    private String content;
    private String title;

    @ManyToOne
    @JoinColumn(name = "FK_UserId")
    private User user;

}
