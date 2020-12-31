package ee.vovtech.backend4cash.phones;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity @Getter @Setter
public class App {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String author;
    private LocalDate latestUpdate;
}
