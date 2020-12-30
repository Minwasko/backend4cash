package ee.vovtech.backend4cash.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Setter @NoArgsConstructor
public class News {
    
    @Id
    @GeneratedValue(generator = "post_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "post_id_seq", sequenceName = "post_id_seq", allocationSize = 50)
    private long id;    

    private String title;
    private String message;

    public News(String title, String message){
        this.title = title;
        this.message = message;
    }

}
