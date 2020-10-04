package ee.vovtech.backend4cash.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Currency {

    @Id
    private String name;
    private String description;
    private String homepageLink;
    private String imageRef;
    @OneToOne(cascade = {CascadeType.ALL})
    private CurrencyPrice currencyPrice;

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
}
