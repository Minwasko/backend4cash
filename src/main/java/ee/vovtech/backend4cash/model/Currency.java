package ee.vovtech.backend4cash.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
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

    public Currency() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHomepageLink() {
        return homepageLink;
    }

    public void setHomepageLink(String homepageLink) {
        this.homepageLink = homepageLink;
    }

    public String getImageRef() {
        return imageRef;
    }

    public void setImageRef(String imageRef) {
        this.imageRef = imageRef;
    }

    public List<TimestampPrice> getTimestampPrices() {
        return timestampPrices;
    }

    public void setTimestampPrices(List<TimestampPrice> timestampPrices) {
        this.timestampPrices = timestampPrices;
    }
}
