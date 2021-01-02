package ee.vovtech.backend4cash.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity @Getter @Setter @NoArgsConstructor
@Table(name = "coins")
public class Currency {

    @Id
    @JoinColumn(name = "name")
    private String name;
    @JoinColumn(name = "description")
    private String description;
    @JoinColumn(name = "homepage_link")
    private String homepageLink;
    @JoinColumn(name = "image_link")
    private String imageRef;

    @OneToMany(mappedBy = "currency", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<TimestampPrice> timestampPrices;
}
