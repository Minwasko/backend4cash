package ee.vovtech.backend4cash.phones;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

@Entity @Getter @Setter
public class Phone {

    @Id
    private String name;
    private String manufacturer;
    private LocalDate releaseDate;
    private float price;
    @OneToMany
    private List<App> apps;
}
