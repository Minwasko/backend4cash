package ee.vovtech.backend4cash.phones;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class App {

    @Id
    @GeneratedValue
    private String name;
    private String author;
    private LocalDate latestUpdate;
}
