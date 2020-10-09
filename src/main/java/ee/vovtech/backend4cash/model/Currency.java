package ee.vovtech.backend4cash.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import java.lang.annotation.Inherited;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Transient
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private CurrencyPrice currencyPrice;

    @OneToMany(mappedBy = "currency", cascade = CascadeType.ALL)
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

    public CurrencyPrice getCurrencyPrice() {
        return currencyPrice;
    }

    public void setCurrencyPrice(CurrencyPrice currencyPrice) {
        this.currencyPrice = currencyPrice;
    }

    public List<TimestampPrice> getTimestampPrices() {
        return timestampPrices;
    }

    public void setTimestampPrices(List<TimestampPrice> timestampPrices) {
        this.timestampPrices = timestampPrices;
    }
}
