package ee.vovtech.backend4cash.phones;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Phone {

    @Id
    @GeneratedValue
    private String name;
    private String manufacturer;
    private LocalDate releaseDate;
//    ... many more
    @OneToMany
    private List<App> apps;
}
